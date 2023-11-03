package com.alvis.media.service.impl;

import com.alvis.media.domain.VideoPlay;
import com.alvis.media.domain.other.KeyValue;
import com.alvis.media.repository.MediaBaseMapper;
import com.alvis.media.repository.VideoPlayMapper;
import com.alvis.media.service.VideoPlayService;
import com.alvis.media.utility.DateTimeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VideoPlayServiceImpl extends BaseServiceImpl<VideoPlay> implements VideoPlayService {
    private final VideoPlayMapper videoPlayMapper;

    public VideoPlayServiceImpl(MediaBaseMapper<VideoPlay> baseMapper, VideoPlayMapper videoPlayMapper) {
        super(baseMapper);
        this.videoPlayMapper = videoPlayMapper;
    }

    /**
     * 查询本月视频播放次数
     */
    @Override
    public int selectVideoPlayCount(VideoPlay videoPlay) {
        // 封装查询条件
        videoPlay.setLastPlayTime(DateTimeUtil.getMonthStartDay());

        // 调用DAO并返回 查询本月视频播放次数
        Integer count = videoPlayMapper.selectVideoPlayCount(videoPlay);

        return count == null ? 0 : count;
    }
    /**
     *  查询当月最佳视频
     */
    @Override
    public String selectBestVideo(VideoPlay videoPlay)
    {
        // 封装查询条件
        videoPlay.setLastPlayTime(DateTimeUtil.getMonthStartDay());
        // 调用DAO并返回  当月的最佳影片
        return videoPlayMapper.selectBestVideo(videoPlay);
    }

    /**
     * 指定开始时间和结束时间查询视频的播放次数
     */
    @Override
    public List<Integer> selectMothCount()
    {
        Date startTime = DateTimeUtil.getMonthStartDay();
        Date endTime = DateTimeUtil.getMonthEndDay();
        List<KeyValue> mouthCount = videoPlayMapper.selectCountByDate(startTime, endTime);
        List <String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();

// 将List<KeyValue> --> 转换成List<Integer>
        List<Integer>  playCountList = new ArrayList<>();
        for(KeyValue keyValue : mouthCount)
        {
            playCountList.add(keyValue.getValue());
        }
        return playCountList;
    }
}
