package com.zyx.domin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名
    private String categoryName;
    //访问量
    private Long viewCount;

    private Date createTime;
    //内容
    private String content;
    //分类id
    private Long categoryId;


}
