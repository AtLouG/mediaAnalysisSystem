package com.alvis.media.controller.admin;

import com.alvis.media.base.BaseApiController;
import com.alvis.media.base.RestResponse;
import com.alvis.media.domain.other.KeyValue;
import com.alvis.media.domain.User;
import com.alvis.media.domain.UserEventLog;
import com.alvis.media.domain.enums.UserStatusEnum;
import com.alvis.media.service.AuthenticationService;
import com.alvis.media.service.UserEventLogService;
import com.alvis.media.service.UserService;
import com.alvis.media.utility.DateTimeUtil;
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


    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserResponseVM>> pageList(@RequestBody UserPageRequestVM model) {

        return RestResponse.ok();
    }


    @RequestMapping(value = "/event/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserEventLogVM>> eventPageList(@RequestBody UserEventPageRequestVM model) {

        return RestResponse.ok();
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> select(@PathVariable Integer id) {

        return RestResponse.ok();
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> current() {
        User user = getCurrentUser();
        UserResponseVM userVm = UserResponseVM.from(user);
        return RestResponse.ok(userVm);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<User> edit(@RequestBody @Valid UserCreateVM model) {

        return RestResponse.ok();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody @Valid UserUpdateVM model) {

        return RestResponse.ok();
    }


    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public RestResponse<Integer> changeStatus(@PathVariable Integer id) {

        return RestResponse.ok();
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {

        return RestResponse.ok();
    }


    @RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
    public RestResponse<List<KeyValue>> selectByUserName(@RequestBody String userName) {

        return RestResponse.ok();
    }



    @RequestMapping(value = "/getUserDetailByUserId/{id}", method = RequestMethod.POST)
    public RestResponse<UserResponseVM> getUserDetailByUserId(@PathVariable Integer id) {

        return RestResponse.ok();
    }
}
