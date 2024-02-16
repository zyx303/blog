package com.zyx.controller;

import com.zyx.domin.ResponseResult;
import com.zyx.domin.dto.AddLinkDto;
import com.zyx.domin.entity.Link;
import com.zyx.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult list(Long pageNum, Long pageSize,String name,Integer status){
        return linkService.getAllLinkPage(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto){
        return linkService.addLink(addLinkDto);
    }

    @GetMapping("{id}")
    public ResponseResult getLink(@PathVariable Long id){
        return ResponseResult.okResult(linkService.getById(id));
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
