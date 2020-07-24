package com.streaming.processor.service;

import java.io.IOException;

public interface VideoProcessorService {
    void processVideos(String bucketName, String path) throws InterruptedException, IOException;
}
