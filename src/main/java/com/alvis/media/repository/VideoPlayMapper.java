package com.alvis.media.repository;

import com.alvis.media.domain.VideoPlay;
import com.alvis.media.domain.other.KeyValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface VideoPlayMapper extends MediaBaseMapper<VideoPlay>
{
    /**
     * 新增视频播放操作
     */
    int insert(VideoPlay record);

    /**
     * 视频播放列表查询
     */
    List<VideoPlay> selectVideoPlayInfo(VideoPlay videoPlay);

    /**
     * 根据条件插入视频播放操作
     */
    int insertSelective(VideoPlay record);

    /**
     * 根据条件(videoId和userId)修改视频播放信息
     */
    int updateByPrimaryKeySelective(VideoPlay record);

    /**
     * 查询本月视频播放次数
     */
    Integer selectVideoPlayCount(VideoPlay videoPlay);

    /**
     * 查询当月最佳视频
     */
    String selectBestVideo(VideoPlay videoPlay);

    /**
     * 指定开始时间和结束时间查询视频的播放次数
     */
    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}