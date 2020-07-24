package com.streaming.utils.s3;

import java.io.File;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

public final class S3Downloader {

    private S3Downloader() {
        // TODO Auto-generated constructor stub
    }

    public static void downloadDirectoriesFromS3(final S3Detail s3Detail, final String bucket, final String path, final String dirPath) throws AmazonClientException, InterruptedException {
        AmazonS3  client = S3Uploader.getClient(s3Detail);
        TransferManager xferMgr = TransferManagerBuilder.standard().withS3Client(client).build();

        try {
            MultipleFileDownload xfer = xferMgr.downloadDirectory(bucket, path, new File(dirPath));
            xfer.waitForCompletion();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}
