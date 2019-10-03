package com.streaming.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.streaming.bean.jpa.VideoEntity;

public interface VideoRepository extends PagingAndSortingRepository<VideoEntity, Integer> {
    List<VideoEntity> findByActiveTrue();

    List<VideoEntity> findByCategoryAndActiveTrue(String category);

    List<VideoEntity> findByCategoryInAndActiveTrue(List<String> categories);
}
