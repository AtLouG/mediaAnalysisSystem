package com.alvis.media.service;

import com.alvis.media.domain.VideoPlay;

import java.util.List;

public interface VideoPlayService  extends BaseService<VideoPlay>
{
    /**
     * 查询本月视频播放次数
     */
    int selectVideoPlayCount(VideoPlay videoPlay);


    /**
     * 查询当月最佳视频
     */
    String selectBestVideo(VideoPlay videoPlay);

    /**
     * 指定开始时间和结束时间查询视频的播放次数
     */
    List<Integer> selectMothCount();
}

