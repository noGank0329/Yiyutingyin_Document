package com.yiyu.pojo.GetCollection;

import lombok.Data;

@Data
public class GetCollection {
    private String blog_id;
    private String blog_title;
    private String blog_content;
    private String blog_time;
    private Integer blog_comment_num;
    private Integer blog_collect_num;

}
