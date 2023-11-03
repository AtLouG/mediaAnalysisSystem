package com.alvis.media.controller.admin;

import com.alvis.media.base.BaseApiController;
import com.alvis.media.base.RestResponse;
import com.alvis.media.domain.User;
import com.alvis.media.service.UserEventLogService;
import com.alvis.media.service.UserService;
import com.alvis.media.service.VideoInfoService;
import com.alvis.media.service.VideoPlayService;
import com.alvis.media.utility.DateTimeUtil;
import com.alvis.media.viewmodel.admin.dashboard.IndexVM;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("AdminDashboardController")
@RequestMapping(value = "/api/admin/dashboard")
@AllArgsConstructor
public class DashboardController extends BaseApiController {

    private UserService userService;

    private VideoInfoService videoInfoService;

    private VideoPlayService videoPlayService;

    private UserEventLogService userEventLogService;

    /**
     * 系统首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> Index() {
        IndexVM vm = new IndexVM();
        // -------------------1.本月新增用户总数---------------
        // step1: 字段封装
        User user = getCurrentUser();

        // step2：调用service层查询本月新增用户方法得到结果
        int count = userService.selectUserCount(user);

        // step3: 将结果封装返回给前端
        vm.setNewUserCount(count);

        // -------------------2.查询本月新增视频数---------------
        // (1)调用service 去数据库查找新增视频个数
        int newVideoCount = videoInfoService.selectNewVideoCount(new VideoInfo());

        // (2)封装返回信息
        vm.setNewVideoCount(newVideoCount);

        // -------------------3.查询本月视频播放次数---------------
        // 1.调用service 去数据库查找数据
        int videoPlayCount = videoPlayService.selectVideoPlayCount(new VideoPlay());

        // 2.封装返回信息
        vm.setDoPlayVideoCount(videoPlayCount);

        // -------------------4.查询本月最佳视频---------------
        // step1.调用service 去数据库查找数据  查询本月最佳视频
        String bestVideo = videoPlayService.selectBestVideo(new VideoPlay());

        // step2.封装返回信息
        vm.setHotVideoCount(bestVideo);


        // -------------------5.用户活跃度--------------
        // 指定开始时间和结束时间查询用户活跃度
        List<Integer> mothDayUserActionValue = userEventLogService.selectMothCount();
        vm.setMothDayUserActionValue(mothDayUserActionValue); // 设置用户活跃度
        vm.setMothDayText(DateTimeUtil.MothDay()); // 设置要查询的月份的最后一天


        // -------------------6.视频月播放次数--------------
        // 指定开始时间和结束时间查询视频的播放次数
        List<Integer> mothDayVideoPlayValue = videoPlayService.selectMothCount();
        vm.setMothDayVideoPlayValue(mothDayVideoPlayValue); // 设置视频月播放次
        vm.setMothDayText(DateTimeUtil.MothDay());          // 设置视频月播放次
        return RestResponse.ok(vm);

    }
}
