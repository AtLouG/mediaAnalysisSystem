package com.alvis.media.controller.admin;


import com.alvis.media.base.BaseApiController;
import com.alvis.media.base.RestResponse;
import com.alvis.media.domain.Message;
import com.alvis.media.domain.MessageUser;
import com.alvis.media.domain.User;
import com.alvis.media.service.MessageService;
import com.alvis.media.service.UserService;
import com.alvis.media.utility.DateTimeUtil;
import com.alvis.media.utility.PageInfoHelper;
import com.alvis.media.viewmodel.admin.message.MessagePageRequestVM;
import com.alvis.media.viewmodel.admin.message.MessageResponseVM;
import com.alvis.media.viewmodel.admin.message.MessageSendVM;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController("AdminMessageController")
@RequestMapping(value = "/api/admin/message")
@AllArgsConstructor
public class MessageController extends BaseApiController {

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<MessageResponseVM>> pageList(@RequestBody MessagePageRequestVM model) {
        return RestResponse.ok();
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public RestResponse send(@RequestBody @Valid MessageSendVM model) {
        return RestResponse.ok();
    }

}
