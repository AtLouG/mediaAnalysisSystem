package com.alvis.media.service.impl;

import com.alvis.media.domain.Tag;
import com.alvis.media.domain.UserTag;
import com.alvis.media.domain.enums.UserTagEnum;
import com.alvis.media.domain.other.KeyValue;
import com.alvis.media.exception.BusinessException;
import com.alvis.media.domain.User;
import com.alvis.media.event.OnRegistrationCompleteEvent;
import com.alvis.media.repository.TagMapper;
import com.alvis.media.repository.UserMapper;
import com.alvis.media.repository.UserTagMapper;
import com.alvis.media.service.UserService;
import com.alvis.media.utility.DateTimeUtil;
import com.alvis.media.viewmodel.admin.user.UserPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author 奇趣
 */
@Service

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final static String CACHE_NAME = "User";

    private final UserMapper userMapper;
    private final TagMapper tagMapper;
    private final UserTagMapper userTagMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, TagMapper tagMapper, UserTagMapper userTagMapper, ApplicationEventPublisher eventPublisher) {
        super(userMapper);
        this.userMapper = userMapper;
        this.tagMapper = tagMapper;
        this.userTagMapper = userTagMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<User> getUsers() {
        return userMapper.getAllUser();
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#username", unless = "#result == null")
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#record.userName")
    public int insertByFilter(User record) {
        //t_user
       int result= super.insertByFilter(record);
        //step 2获取tag——id
        if(null!=record.getUserTagList() &&!record.getUserTagList().isEmpty())
        {
            //step1 获取userId
            User user = getUserByUserName(record.getUserName());
            Integer userId=user.getId();
            //step2:tagId
            List <UserTag>  userTagList =  record.getUserTagList();
            for(UserTag tag:userTagList)
            {
                Tag filter = new Tag();
                filter.setTagName(tag.getTagName());
              Tag tagDb =  tagMapper.selectTagByTagName(filter);
              if(null == tagDb)
              {
                  tagMapper.insertSelective(filter);
                   tagDb =  tagMapper.selectTagByTagName(filter);
              }

              Integer tagId=tagDb.getTagId();
              // 插入t_user_tag
                UserTag userTagRedord = new UserTag();
                userTagRedord.setTagId(tagId);
                userTagRedord.setUserId(userId);
                userTagRedord.setUserPropertyFlag(UserTagEnum.SELF.getCode());
                userTagMapper.insertSelective(userTagRedord);
            }
        }
        return 0;
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#record.userName")
    public int updateByIdFilter(User record) {
        return super.updateByIdFilter(record);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#record.userName")
    public int updateById(User record) {
        return super.updateById(record);
    }

    @Override
    public PageInfo<User> userPage(UserPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                userMapper.userPage(requestVM)
        );
    }


    @Override
    public List<User> selectByIds(List<Integer> ids) {
        return userMapper.selectByIds(ids);
    }

    @Override
    public User selectByWxOpenId(String wxOpenId) {
        return userMapper.selectByWxOpenId(wxOpenId);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, key = "#user.userName")
    @Transactional
    public void changePicture(User user, String imagePath) {
        User changePictureUser = new User();
        changePictureUser.setId(user.getId());
        changePictureUser.setImagePath(imagePath);
        userMapper.updateByPrimaryKeySelective(changePictureUser);
    }

    @Override
    public int selectUserCount(User user) {
        // 当月的用户新增用户数量
        // 封装查询条件
        user.setCreateTime(DateTimeUtil.getMonthStartDay());

        // 调用DAO并返回
        return userMapper.selectUserCount(user);
    }
}
