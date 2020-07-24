package com.streaming.utils.s3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

/**
 * @author mydeziner.com
 */
public final class S3Uploader {
    private static Logger logger = LoggerFactory.getLogger(S3Uploader.class);

    // The map is required since, one cannot access the buckets of different region
    // from the clients of the different region
    private static Map<String, AmazonS3> s3clients = new HashMap<String, AmazonS3>();

    private S3Uploader() {

    }

    public static AmazonS3 getClient(final S3Detail s3Details) {
        if (s3clients.containsKey(s3Details.getRegion())) {
            return s3clients.get(s3Details.getRegion());
        }

        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
        AWSCredentialsProvider provider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(s3Details.getAccessKeyId(), s3Details.getScretAcceessKey()));
        builder.setCredentials(provider);
        builder.setRegion(s3Details.getRegion());

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        clientConfig.setCacheResponseMetadata(false);
        builder.setClientConfiguration(new ClientConfiguration());

        AmazonS3 client = builder.build();

        s3clients.put(s3Details.getRegion(), client);

        return client;
    }

    public static String uploadImage(final S3Detail s3Detail, final String bucket, final byte[] base64Image,
            final String fileName) {
        logger.debug("UploadImage start params: bucket:" + bucket + " fileName:" + fileName);

        AmazonS3 client = getClient(s3Detail);

        if (s3Detail.getCreateBucketIfNotExists()) {
            createBucketIfNotExists(bucket, client);
        }

        String file = createFile(fileName, bucket, client, base64Image);

        logger.debug("UploadImage end");
        return file;
    }

    public static String uploadImage(final S3Detail s3Detail, final String bucket, final String base64Image,
            final String fileName) {
        logger.debug("UploadImage start params: bucket:" + bucket + " fileName:" + fileName);

        byte[] data = Base64.decodeBase64(base64Image);
        AmazonS3 client = getClient(s3Detail);

        if (s3Detail.getCreateBucketIfNotExists()) {
            createBucketIfNotExists(bucket, client);
        }

        String file = createFile(fileName, bucket, client, data);

        logger.debug("UploadImage end");
        return file;
    }

    public static String uploadFile(final S3Detail s3Detail, final String bucket, final File file,
            final String fileName) {
        logger.debug("UploadFile start params: bucket:" + bucket + " fileName:" + fileName);

        AmazonS3 client = getClient(s3Detail);

        if (s3Detail.getCreateBucketIfNotExists()) {
            createBucketIfNotExists(bucket, client);
        }

        PutObjectRequest putRequest = new PutObjectRequest(bucket, fileName, file);
        putRequest = putRequest.withCannedAcl(CannedAccessControlList.PublicRead);

        logger.debug("client.putObject called");
        client.putObject(putRequest);

        logger.debug("createFile end");
        return client.getUrl(bucket, fileName).toString();
    }

    public static List<String> getFilesByPath(final S3Detail s3Detail, final String bucket, final String path) {
        AmazonS3 client = getClient(s3Detail);

        return S3Uploader.getFilesByPath(bucket, path, client);
    }

    public static List<String> getFilesByPath(final String bucket, final String path, final AmazonS3 client) {
        List<String> files = new ArrayList<>();

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucket).withPrefix(path)
                .withMarker(path + "/");

        ObjectListing objectListing = client.listObjects(listObjectsRequest);

        for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
            files.add(bucket + "/" + summary.getKey());
        }

        return files;
    }

    private static void createBucketIfNotExists(final String bucketName, final AmazonS3 client) {
        boolean bucketExists = false;
        for (Bucket bucket : client.listBuckets()) {
            if (bucket.getName().equals(bucketName)) {
                bucketExists = true;
                break;
            }
        }

        if (!bucketExists) {
            client.createBucket(bucketName);
        }
    }

    private static String createFile(final String fileName, final String bucketName, final AmazonS3 client,
            final byte[] bytes) {
        logger.debug("createFile start");
        InputStream stream = new ByteArrayInputStream(bytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        metadata.setContentType("image/jpeg");

        PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileName, stream, metadata);
        putRequest = putRequest.withCannedAcl(CannedAccessControlList.PublicRead);

        logger.debug("client.putObject called");
        client.putObject(putRequest);

        logger.debug("createFile end");
        return client.getUrl(bucketName, fileName).toString();
    }

    public static void copyObject(final S3Detail s3Detail, final String sourceBucket, final String destBucket,
            final String path) {
        CopyObjectRequest copyObjRequest = new CopyObjectRequest(sourceBucket, path, destBucket, path);
        AmazonS3 client = getClient(s3Detail);
        client.copyObject(copyObjRequest);
    }

    public static void uploadFiles(String bucketName, String path, List<File> files, String relativePath, S3Detail s3Detail) throws InterruptedException {
        AmazonS3  client = S3Uploader.getClient(s3Detail);
        TransferManager xferMgr = TransferManagerBuilder.standard().withS3Client(client).build();

        try {
            MultipleFileUpload xfer = xferMgr.uploadFileList(bucketName, path, new File(relativePath), files);
            xfer.waitForCompletion();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}
