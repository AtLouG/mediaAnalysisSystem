package com.alvis.media.service;

import com.alvis.media.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TagService extends IService<Tag> {
    List <Tag> queryAll();
}
