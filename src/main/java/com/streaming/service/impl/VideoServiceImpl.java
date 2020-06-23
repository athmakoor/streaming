package com.streaming.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.streaming.bean.Video;
import com.streaming.bean.jpa.VideoEntity;
import com.streaming.repository.VideoRepository;
import com.streaming.service.VideoService;
import com.streaming.service.mapping.ServiceMapper;

@Component
public class VideoServiceImpl implements VideoService {
    @Resource
    private ServiceMapper<Video, VideoEntity> videoMapper;
    @Resource
    private VideoRepository videoRepository;

    @Override
    public List<Video> findByActiveTrue() {
        return videoMapper.mapEntitiesToDTOs(videoRepository.findByActiveTrue(), Video.class);
    }

    @Override
    public List<Video> findByCategoryActiveTrue(String category) {
        return videoMapper.mapEntitiesToDTOs(videoRepository.findByCategoryAndActiveTrue(category), Video.class);
    }

    @Override
    public Map<String, List<Video>> findByCategoriesActiveTrue(List<String> categories) {
        Map<String,List<Video>> map =new HashMap<>();
        String category;

        List<Video> games = videoMapper.mapEntitiesToDTOs(videoRepository.findByCategoryInAndActiveTrue(categories), Video.class);

        for (Video game : games) {
            category = game.getCategory();
            map.putIfAbsent(category, new ArrayList<>());
            map.get(category).add(game);
        }

        return map;
    }

    @Override
    public Map<String, List<Video>> findByCategoriesGroupByCategory() {
        Map<String,List<Video>> map =new HashMap<>();
        String category;

        List<Video> videos = videoMapper.mapEntitiesToDTOs(videoRepository.findByActiveTrue(), Video.class);

        for (Video video : videos) {
            category = video.getCategory();
            map.putIfAbsent(category, new ArrayList<>());

            if (map.get(category).size() < 6) {
                map.get(category).add(video);
            }
        }

        return map;
    }

    @Override
    public Video findById(Integer id) {
        Optional<VideoEntity> videoEntityOptional = videoRepository.findById(id);

        if (videoEntityOptional.isPresent()) {
            return videoMapper.mapEntityToDTO(videoEntityOptional.get(), Video.class);
        }

        return null;
    }
}
