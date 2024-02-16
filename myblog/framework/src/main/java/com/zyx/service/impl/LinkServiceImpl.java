package com.zyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyx.constants.SystemConstants;
import com.zyx.domin.ResponseResult;
import com.zyx.domin.dto.AddLinkDto;
import com.zyx.domin.entity.Link;
import com.zyx.domin.vo.LinkVo;
import com.zyx.domin.vo.PageVo;
import com.zyx.mapper.LinkMapper;
import com.zyx.service.LinkService;
import com.zyx.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2024-02-15 12:41:55
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有友链，status=1
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        return ResponseResult.okResult(list(queryWrapper));
    }

    @Override
    public ResponseResult getAllLinkPage(Long pageNum, Long pageSize,String name,Integer status) {
        //查询所有友链,若有name则模糊查询，若有status则根据status查询
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !"".equals(name)){
            queryWrapper.like(Link::getName,name);
        }
        if (status != null){
            queryWrapper.eq(Link::getStatus,status);
        }
        //分页查询
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        //封装LinkVo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkVo.class);
        //封装pagevo
        return ResponseResult.okResult(new PageVo(linkVos,page.getTotal()));
    }

    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        //添加友链
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }
}

