package com.zyx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyx.domin.entity.Tag;
import com.zyx.mapper.TagMapper;
import com.zyx.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-02-02 21:38:52
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

