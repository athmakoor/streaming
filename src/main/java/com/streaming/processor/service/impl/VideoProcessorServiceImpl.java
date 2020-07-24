package com.streaming.processor.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.streaming.bean.Video;
import com.streaming.processor.service.VideoProcessorService;
import com.streaming.service.VideoService;
import com.streaming.utils.VideoProcessorUtil;
import com.streaming.utils.s3.S3Detail;

@Service
public class VideoProcessorServiceImpl implements VideoProcessorService {
    @Resource
    private S3Detail s3Detail;
    @Resource
    private VideoService videoService;

    @Override
    public void processVideos(String bucketName, String path) throws IOException, InterruptedException {
        List<Video> videos = VideoProcessorUtil.processFilesAt(bucketName, path, s3Detail);

        videoService.save(videos);
    }
}
