package com.alvis.media.service;

import com.alvis.media.domain.VideoInfo;
import com.alvis.media.viewmodel.video.VideoPageRequestVM;
import com.github.pagehelper.PageInfo;


public interface VideoInfoService extends BaseService<VideoInfo> {

   PageInfo<VideoInfo> videoPage(VideoPageRequestVM requestVM);

   VideoInfo getPageInfoById(Integer id);

}
