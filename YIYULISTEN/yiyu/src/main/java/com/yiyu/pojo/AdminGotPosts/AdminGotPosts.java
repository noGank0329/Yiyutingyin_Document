package com.yiyu.pojo.AdminGotPosts;

import lombok.Data;

//用于返回管理员获取所有未审核的帖子
@Data
public class AdminGotPosts {

    private String blog_id;
    private String user_id;
    private String user_nickname;
    private String blog_title;
    private String blog_content;
    private Integer blog_collect_num;
    private Integer blog_comment_num;
    private String blog_time;
}
