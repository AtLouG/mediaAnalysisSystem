package com.alvis.media.controller.admin;

import com.alvis.media.base.BaseApiController;
import com.alvis.media.base.RestResponse;
import com.alvis.media.domain.User;
import com.alvis.media.domain.UserEventLog;
import com.alvis.media.domain.enums.UserStatusEnum;
import com.alvis.media.domain.other.KeyValue;
import com.alvis.media.service.AuthenticationService;
import com.alvis.media.service.UserEventLogService;
import com.alvis.media.service.UserService;
import com.alvis.media.utility.PageInfoHelper;
import com.alvis.media.viewmodel.admin.user.*;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController("AdminUserController")
@RequestMapping(value = "/api/admin/user")
@AllArgsConstructor
public class UserController extends BaseApiController {

    private final UserService userService;
    private final UserEventLogService userEventLogService;
    private final AuthenticationService authenticationService;


    //    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
//    public RestResponse<PageInfo<UserResponseVM>> pageList(@RequestBody UserPageRequestVM model) {
//
//        return RestResponse.ok();
//    }
    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserResponseVM>> pageList(@RequestBody UserPageRequestVM model){
        PageInfo<User> userPageInfo = userService.userPage(model);
        PageInfo<UserResponseVM> page = PageInfoHelper.copyMap(userPageInfo,u -> UserResponseVM.from(u));
        return RestResponse.ok(page);
    }



    @RequestMapping(value = "/event/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserEventLogVM>> eventPageList(@RequestBody UserEventPageRequestVM model) {
        PageInfo<UserEventLog> userEventLogPageInfo = userEventLogService.page(model);

        PageInfo<UserEventLogVM> page = PageInfoHelper.copyMap(userEventLogPageInfo, d -> UserEventLogVM.from(d));

        return RestResponse.ok(page);
//        return RestResponse.ok();
    }
//
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> select(@PathVariable Integer id)
    {
        // 调用用户Service层的 根据用户ID查询某个指定的用户信息（用于用户信息的修改）的方法
        User user = userService.getUserById(id);
        UserResponseVM userResponseVM = UserResponseVM.from(user);
        return RestResponse.ok(userResponseVM);
    }

    //
    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        User user = getCurrentUser();
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }
//
//
//    @RequestMapping(value = "/edit", method = RequestMethod.POST)
//    public RestResponse<User> edit(@RequestBody @Valid UserCreateVM model) {
//
//        return RestResponse.ok();
//    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<User> edit(@RequestBody @Valid UserCreateVM userCreateVM)
    {
        // 将 UserCreateVM --> 转换成 User
        User user = modelMapper.map(userCreateVM, User.class);

        // 没有获取到用户ID --> 则做用户新增
        if (userCreateVM.getId() == null)
        {
            // 新增用户时判断该用户是否存在
            User existUser = userService.getUserByUserName(userCreateVM.getUserName());
            if (existUser != null)
            {
                // 提示用户已存在
                return new RestResponse<User>(2, "用户已存在");
            }

            if (StringUtils.isBlank(userCreateVM.getPassword()))
            {
                return new RestResponse<>(3, "密码不能为空");
            }

            // 进入 用户新增界面 将用户生日置为null
            if (StringUtils.isBlank(userCreateVM.getBirthDay()))
            {
                userCreateVM.setBirthDay(null);
            }

            // 用户新增
            // 密码加密
            String encodePwd = authenticationService.pwdEncode(userCreateVM.getPassword());
            user.setPassword(encodePwd);            // 设置用户密码
            user.setUserUuid(UUID.randomUUID().toString());
            user.setCreateTime(new Date());     // 设置用户创建时间
            user.setLastActiveTime(new Date()); // 设置 用户最后活跃时间
            user.setDeleted(false);  // 设置 是否删除用户  软删除（0-未删除  1-删除 将0-1）

            userService.insertByFilter(user);  // (1)用户新增
        }else    // 获取到用户ID --> 则做用户修改
        {
            // 用户修改（编辑）
            if (!StringUtils.isBlank(userCreateVM.getPassword()))
            {
                String encodePwd = authenticationService.pwdEncode(userCreateVM.getPassword());
                user.setPassword(encodePwd);
            }// end of inner if
        }// end of outer if

        user.setModifyTime(new Date());
        userService.updateByIdFilter(user); // (2)用户修改
        return RestResponse.ok(user);
    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public RestResponse update(@RequestBody @Valid UserUpdateVM model) {
//
//        return RestResponse.ok();
//    }
//
//
    @RequestMapping(value = "changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
        Integer newStatus = userStatusEnum == UserStatusEnum.Enable ? UserStatusEnum.Disable.getCode() : UserStatusEnum.Enable.getCode();
        user.setStatus(newStatus);
        user.setModifyTime(new Date());
        userService.updateByIdFilter(user);
        return RestResponse.ok(newStatus);
    }
//
//
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
//    public RestResponse delete(@PathVariable Integer id) {
//
//        return RestResponse.ok();
//    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        int i = userService.deleteById(id);
        return RestResponse.ok();
    }
//
//
    @RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
    public RestResponse<List<KeyValue>> selectByUserName(@RequestBody String userName) {

        return RestResponse.ok();
    }
//
//
//
    @RequestMapping(value = "/getUserDetailByUserId/{id}", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> getUserDetailByUserId(@PathVariable Integer id) {

        return RestResponse.ok();
    }


}
