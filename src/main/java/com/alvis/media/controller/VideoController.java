package com.alvis.media.controller;

import com.alvis.media.base.BaseApiController;
import com.alvis.media.base.NonStaticResourceHttpRequestHandler;
import com.alvis.media.base.RestResponse;
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

@RestController("VideoController")
@RequestMapping(value = "/api/admin/video")
@AllArgsConstructor
public class VideoController  extends BaseApiController {


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

        return null;
    }

    @RequestMapping(value = "/getVideoDetailByVideoId/{id}", method = RequestMethod.POST)
    public RestResponse <VideoDetailVM> getVideoDetailByVideoId(@PathVariable Integer id) {

        return null;
    }

    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo <VideoResponseVM>> pageList(@RequestBody VideoPageRequestVM model) {
        return null;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RestResponse<VideoResponseVM> createVideo(@RequestBody @Valid VideoCreateVM model) {

        return RestResponse.ok();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<VideoResponseVM> editVideo(@RequestBody @Valid VideoCreateVM model) {
        return RestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        return RestResponse.ok();
    }


    @RequestMapping(value = "/userAnalysis/{id}", method = RequestMethod.POST)
    public RestResponse <UserAnalysisVM> userAnalysis(@PathVariable Integer id) {

        UserAnalysisVM vm = new UserAnalysisVM();

        return RestResponse.ok(vm);

    }
}
