package com.alvis.media.service.impl;

import com.alvis.media.domain.VideoInfo;
import com.alvis.media.repository.VideoInfoMapper;
import com.alvis.media.service.VideoInfoService;
import com.alvis.media.viewmodel.video.VideoPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoInfoServiceImpl extends  BaseServiceImpl<VideoInfo> implements VideoInfoService {
    private VideoInfoMapper videoInfoMapper;
    @Autowired
    public VideoInfoServiceImpl(VideoInfoMapper videoInfoMapper ) {
        super(videoInfoMapper);
        this.videoInfoMapper=videoInfoMapper;
    }


    @Override
    public PageInfo<VideoInfo> videoPage(VideoPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(),requestVM.getPageSize(),"video_id desc").doSelectPageInfo(() ->
                videoInfoMapper.videoPage(requestVM));

    }

    @Override
    public VideoInfo getPageInfoById(Integer id) {
        return videoInfoMapper.selectByPrimaryKey(id);
    }


}