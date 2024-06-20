package com.yiyu.pojo.UserGotPosts;

import lombok.Data;

import java.util.List;
//        用于返回用户查看所有审核通过的帖子响应的嵌套
@Data
public class UserGotPostsResponse {
    private List<UserGotPosts> all_post;
    public UserGotPostsResponse(List<UserGotPosts> all_post) {
        this.all_post = all_post;
    }


}
