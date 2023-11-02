package com.alvis.media.controller;

import com.alvis.media.base.BaseApiController;
import com.alvis.media.base.NonStaticResourceHttpRequestHandler;
import com.alvis.media.base.RestResponse;
import com.alvis.media.domain.User;
import com.alvis.media.domain.VideoInfo;
import com.alvis.media.service.VideoInfoService;
import com.alvis.media.utility.PageInfoHelper;
import com.alvis.media.viewmodel.video.*;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController("VideoController")
@RequestMapping(value = "/api/admin/video")
@AllArgsConstructor
public class VideoController extends BaseApiController {
    private  VideoInfoService videoInfoService;

    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    @GetMapping("/play/**/*")
    public void playVideo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //realPath 即视频所在的完整地址
        String url = request.getRequestURL().toString();
        int index=  url.lastIndexOf("D:");
        String realPath = url.substring(index);

        Path filePath = Paths.get(realPath);
        if (Files.exists(filePath)) {
            // 利用 Files.probeContentType 获取文件类型
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                // 设置 response
                response.setContentType(mimeType);
            }
            request.setAttribute(nonStaticResourceHttpRequestHandler.filepath, filePath);
            // 利用 ResourceHttpRequestHandler.handlerRequest() 实现返回视频流
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<VideoResponseVM> select(@PathVariable Integer id) {
        VideoInfo videoInfo = videoInfoService.getPageInfoById(id);
        videoInfo.setVideoUrl("/api/admin/video/play/"+videoInfo.getVideoUrl());
        VideoResponseVM videoResponseVM =VideoResponseVM.from(videoInfo);

        return RestResponse.ok(videoResponseVM);
    }

    @RequestMapping(value = "/getVideoDetailByVideoId/{id}", method = RequestMethod.POST)
    public RestResponse <VideoDetailVM> getVideoDetailByVideoId(@PathVariable Integer id) {
        //调用service层getPageInfoById方法根据id获取videoInfo对象
        VideoInfo info = videoInfoService.getPageInfoById(id);
        //新建一个VideoDetailVM对象
        VideoDetailVM videoDetailVM =new VideoDetailVM();
        videoDetailVM.setVideoId(info.getVideoId());
        //为videoDetailVM对象设置VideoName属性为获取info对象的VideoName
        videoDetailVM.setVideoName(info.getVideoName());
        //返回页面所需的细节对象
        return RestResponse.ok(videoDetailVM);
    }

    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo <VideoResponseVM>> pageList(@RequestBody VideoPageRequestVM model) {
        PageInfo<VideoInfo> videoInfoPageInfo = videoInfoService.videoPage(model);

        PageInfo<VideoResponseVM> page = PageInfoHelper.copyMap(videoInfoPageInfo, d -> VideoResponseVM.from(d));

        return RestResponse.ok(page);
    }

//    public RestResponse<PageInfo <VideoResponseVM>> pageList(@RequestBody VideoPageRequestVM model) {
//        PageInfo<VideoInfo> videoInfoPageInfo = VideoInfoService.VideoPage(model);
//
//        PageInfo<VideoResponseVM> page = PageInfoHelper.copyMap(videoInfoPageInfo, d -> VideoResponseVM.from(d));
//
//        return RestResponse.ok(page);
//    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RestResponse<VideoResponseVM> createVideo(@RequestBody @Valid VideoCreateVM model) {
        // 将VideoCreateVM 转换成---> VideoInfo
        VideoInfo videoInfo =  modelMapper.map(model,VideoInfo.class);

        Integer videoCategory = model.getVideoCategory();

        // 调用service层将表单信息插入视频信息表中
        videoInfo.setCreateTime(new Date());
        videoInfo.setLastModifyTime(new Date());
        videoInfo.setVideoCategory(videoCategory);
        User loginUser = getCurrentUser();
        videoInfo.setCreatorId(loginUser.getId());
        videoInfoService.insertByFilter(videoInfo);

        return RestResponse.ok();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<VideoResponseVM> editVideo(@RequestBody @Valid VideoCreateVM model) {
        //调用modelMapper层map方法将VideoCreateVM类型model改为VideoInfo类型
        VideoInfo videoInfo = modelMapper.map(model,VideoInfo.class);
        //设置最后修改时间为当前时间
        videoInfo.setLastModifyTime(new Date());
        //调用service层的updateByIdFilter方法
        videoInfoService.updateByIdFilter(videoInfo);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        int i = videoInfoService.deleteById(id);
        return RestResponse.ok();
    }


    @RequestMapping(value = "/userAnalysis/{id}", method = RequestMethod.POST)
    public RestResponse <UserAnalysisVM> userAnalysis(@PathVariable Integer id) {

        UserAnalysisVM vm = new UserAnalysisVM();

        return RestResponse.ok(vm);

    }
}
