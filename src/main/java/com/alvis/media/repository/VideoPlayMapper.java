package com.alvis.media.repository;

import com.alvis.media.domain.VideoPlay;

public interface VideoPlayMapper {
    int insert(VideoPlay record);

    int insertSelective(VideoPlay record);
}