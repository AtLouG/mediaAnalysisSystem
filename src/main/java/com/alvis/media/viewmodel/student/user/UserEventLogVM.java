package com.alvis.media.viewmodel.student.user;

import com.alvis.media.domain.UserEventLog;
import com.alvis.media.viewmodel.BaseVM;
import lombok.Data;

import java.util.Date;

@Data
public class UserEventLogVM extends BaseVM {

    private Integer id;

    private Integer userId;

    private String userName;

    private String realName;

    private String content;

    private String createTime;

    public static com.alvis.media.viewmodel.admin.user.UserEventLogVM from(UserEventLog userEventLog) {
        com.alvis.media.viewmodel.admin.user.UserEventLogVM vm = modelMapper.map(userEventLog, com.alvis.media.viewmodel.admin.user.UserEventLogVM.class);
        vm.setUserId(userEventLog.getUserId());
        vm.setUserName(userEventLog.getUserName());
        vm.setRealName(userEventLog.getRealName());
        vm.setContent(userEventLog.getContent());
        vm.setCreateTime(String.valueOf(userEventLog.getCreateTime()));
        return vm;
    }

}
