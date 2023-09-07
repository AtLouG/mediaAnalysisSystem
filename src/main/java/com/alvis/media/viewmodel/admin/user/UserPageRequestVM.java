package com.alvis.media.viewmodel.admin.user;

import com.alvis.media.base.BasePage;
import lombok.Data;

@Data
public class UserPageRequestVM extends BasePage {

    private String userName;
    private Integer role;

}
