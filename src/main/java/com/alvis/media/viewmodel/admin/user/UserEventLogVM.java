package com.alvis.media.viewmodel.admin.user;

import com.alvis.media.domain.User;
import com.alvis.media.domain.UserEventLog;
import com.alvis.media.utility.DateTimeUtil;
import com.alvis.media.viewmodel.BaseVM;
import com.alvis.media.viewmodel.student.user.UserResponseVM;
import lombok.Data;

@Data
public class UserEventLogVM extends BaseVM {

    private Integer id;

    private Integer userId;

    private String userName;

    private String realName;

    private String content;

    private String createTime;

    public static UserEventLogVM from(UserEventLog userEventLog) {
        UserEventLogVM vm = modelMapper.map(userEventLog, UserEventLogVM.class);
//        vm.setUserId(userEventLog.getUserId());
//        vm.setUserName(userEventLog.getUserName());
//        vm.setRealName(userEventLog.getRealName());
//        vm.setContent(userEventLog.getContent());
        vm.setCreateTime(DateTimeUtil.dateFormat(userEventLog.getCreateTime()));
        return vm;
    }

}
