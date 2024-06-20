package com.yiyu.pojo.UserGotPosts;

import lombok.Data;

//用于返回用户查看所有审核通过的帖子
@Data
public class UserGotPosts {

    private String user_nickname;
    private String blog_id;
    private String blog_title;
    private String blog_content;
    private Integer blog_collect_num;
    private Integer blog_comment_num;
    private String blog_time;
}
