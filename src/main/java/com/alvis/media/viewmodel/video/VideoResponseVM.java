package com.alvis.media.viewmodel.video;

import com.alvis.media.domain.VideoInfo;
import com.alvis.media.utility.DateTimeUtil;
import com.alvis.media.viewmodel.BaseVM;
import lombok.Data;


@Data
public class VideoResponseVM extends BaseVM {

    private Integer videoId;



    private String videoName;


    private Integer videoCategory;

    private String createTime;

    private Integer creatorId;

    private String lastModifyTime;

    private String videoUrl;


    public static VideoResponseVM from(VideoInfo videoInfo) {
        VideoResponseVM vm = modelMapper.map(videoInfo, VideoResponseVM.class);
        vm.setLastModifyTime(DateTimeUtil.dateFormat(videoInfo.getLastModifyTime()));
        vm.setCreateTime(DateTimeUtil.dateFormat(videoInfo.getCreateTime()));
        return vm;
    }
}
