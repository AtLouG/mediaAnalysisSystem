package com.alvis.media.service;

import com.alvis.media.domain.other.KeyValue;
import com.alvis.media.domain.User;
import com.alvis.media.viewmodel.admin.user.UserPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 *@author 奇趣
 */
public interface UserService extends BaseService<User> {

    /**
     * getUsers
     *
     * @return List<User>
     */
    List<User> getUsers();







    /**
     * getUserById
     *
     * @param id id
     * @return User
     */
    User getUserById(Integer id);

    /**
     * getUserByUserName
     *
     * @param username username
     * @return User
     */
    User getUserByUserName(String username);






    /**
     * @param requestVM requestVM
     * @return PageInfo<User>
     */
    PageInfo<User> userPage(UserPageRequestVM requestVM);




    List<User> selectByIds(List<Integer> ids);

    User selectByWxOpenId(String wxOpenId);

    void changePicture(User user, String imagePath);
}
