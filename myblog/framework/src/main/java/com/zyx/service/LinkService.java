package com.zyx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.dto.AddLinkDto;
import com.zyx.domin.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-02-15 12:41:55
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getAllLinkPage(Long pageNum, Long pageSize,String name,Integer status);

    ResponseResult addLink(AddLinkDto addLinkDto);
}

