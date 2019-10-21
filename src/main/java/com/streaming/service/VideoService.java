package com.streaming.service;

import java.util.List;
import java.util.Map;

import com.streaming.bean.Video;

public interface VideoService {
    List<Video> findByActiveTrue();
    List<Video> findByCategoryActiveTrue(String category);

    Map<String, List<Video>> findByCategoriesActiveTrue(List<String> categories);

    Map<String, List<Video>> findByCategoriesGroupByCategory();
}
