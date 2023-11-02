package com.alvis.media.repository;

import com.alvis.media.domain.VideoInfo;
import com.alvis.media.viewmodel.video.VideoPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoInfoMapper extends MediaBaseMapper<VideoInfo>{
    int deleteByPrimaryKey(Integer videoId);

    int insert(VideoInfo record);

    int insertSelective(VideoInfo record);

    VideoInfo selectByPrimaryKey(Integer videoId);

    int updateByPrimaryKeySelective(VideoInfo record);

    int updateByPrimaryKey(VideoInfo record);

    List<VideoInfo> videoPage(VideoPageRequestVM requestVM);
}