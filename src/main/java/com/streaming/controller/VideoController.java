package com.streaming.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streaming.bean.Video;
import com.streaming.service.VideoService;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    @Resource
    private VideoService videoService;

    @GetMapping("/category/{category}")
    public List<Video> findByCategory(@PathVariable("category") final String category) {
        return videoService.findByCategoryActiveTrue(category);
    }

    @GetMapping("/all")
    public List<Video> allGames() {
        return videoService.findByActiveTrue();
    }

    @GetMapping("/home")
    public Map<String, List<Video>> findAllByCategories() {
        return videoService.findByCategoriesGroupByCategory();
    }

    @GetMapping("/categories/{categories}")
    public Map<String, List<Video>> findByCategories(@PathVariable("categories") final String categories) {
        return videoService.findByCategoriesActiveTrue(Arrays.asList(categories.split(",")));
    }
}
