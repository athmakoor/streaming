package com.streaming.processor;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streaming.processor.service.VideoProcessorService;

@RestController
@RequestMapping("/api/processor")
public class VideoProcessorController {
    @Resource
    VideoProcessorService videoProcessorService;

    @GetMapping("/{bucket}/{path}")
    public String findByCategories(@PathVariable("bucket") final String bucket, @PathVariable("path") final String path) throws InterruptedException, IOException {
        videoProcessorService.processVideos(bucket, path);

        return "Success";
    }
}
