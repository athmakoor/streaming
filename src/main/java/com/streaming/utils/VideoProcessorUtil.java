package com.streaming.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.streaming.bean.Video;
import com.streaming.utils.s3.S3Detail;
import com.streaming.utils.s3.S3Downloader;
import com.streaming.utils.s3.S3Uploader;

public class VideoProcessorUtil {
    public static final String RESOLUTION = "480x360";
    public static final String MULTI_VIDEO_COMPRESSION_COMMAND = "cd {{ROOT}} && mkdir compressed && for i in *; do ffmpeg -i \"$i\" -r 30 -s {{RESOLUTION}} \"compressed/${i%}\"; done";
    public static final String MULTI_IMAGE_EXTRACTION_COMMAND = "cd {{ROOT}} && mkdir icons && for i in *; do ffmpeg -i \"$i\" -ss 00:00:5 -f image2 -vframes 1 \"icons/${i%.*}.png\"; done";

    private static String getVideoCompressionCommand(String path) {
        String command = MULTI_VIDEO_COMPRESSION_COMMAND;

        command = command.replace("{{ROOT}}", path);
        command = command.replace("{{RESOLUTION}}", RESOLUTION);

        return command;
    }

    private static String getImageExtractionCommand(String path) {
        String command = MULTI_IMAGE_EXTRACTION_COMMAND;

        command = command.replace("{{ROOT}}", path);

        return command;
    }

    private static void runCommand(String command) throws IOException {
        String[] commandArgs = new String[] {"/bin/bash", "-c", command, "with", "args"};
        Process proc = new ProcessBuilder(commandArgs).start();

        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<Video> processFilesAt(String bucketName, String path, S3Detail s3Detail) throws IOException, InterruptedException {
        String dirPath = "/home/nischal/Downloads/";
        //S3Downloader.downloadDirectoriesFromS3(s3Detail, bucketName, path, dirPath);

        List<String> files = S3Uploader.getFilesByPath(s3Detail, bucketName, path);
        List<Video> videos = new ArrayList<>();
        Video video;
        String name, category, displayName, videoUrl, imageUrl, extension;
        String[] split, nameSplit;
        Integer length;
        List<String> dirs = new ArrayList<>();
        List<String> videoFiles = new ArrayList<>();
        List<File> uploadFiles = new ArrayList<>();
        File filesList[];

        for (String file : files) {
            if (file.charAt(file.length() - 1) == '/') {
                dirs.add(file);
                String compressionCommand = getVideoCompressionCommand(dirPath + file);
                //runCommand(compressionCommand);
                String imageExtractionCommand = getImageExtractionCommand(dirPath + file);
                //runCommand(imageExtractionCommand);

                File directoryPath = new File(dirPath + file + "icons");

                filesList = directoryPath.listFiles();

                if (filesList != null) {
                    uploadFiles.addAll(Arrays.asList(filesList));
                }

                directoryPath = new File(dirPath + file + "compressed");
                filesList = directoryPath.listFiles();


                if (filesList != null) {
                    uploadFiles.addAll(Arrays.asList(filesList));
                }
            } else {
                videoFiles.add(file);
            }
        }

        if (!uploadFiles.isEmpty()) {
            S3Uploader.uploadFiles(bucketName, "", uploadFiles, dirPath + "mooddit/" + path, s3Detail);
        }

        for (String file : videoFiles) {
            video =new Video();

            split = file.split("/");
            length = split.length;

            name = split[length - 1];
            nameSplit = name.split("\\.");
            name = nameSplit[0];
            extension = nameSplit[1];
            category = split[length - 2];

            displayName = name.replaceAll("_", " ");

            video.setActive(true);
            video.setCategory(category);
            video.setDisplayName(displayName);
            video.setName(name);
            video.setImageUrl(name + ".png");
            video.setVideoUrl(name + "." + extension);

            videos.add(video);
        }

        return videos;
    }
}
