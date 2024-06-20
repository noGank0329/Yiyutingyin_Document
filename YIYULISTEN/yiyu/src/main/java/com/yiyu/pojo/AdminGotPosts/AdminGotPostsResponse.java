package com.yiyu.pojo.AdminGotPosts;

import lombok.Data;

import java.util.List;
@Data
public class AdminGotPostsResponse {
    private List<AdminGotPosts> unchecked_posts;
    public AdminGotPostsResponse(List<AdminGotPosts> unchecked_posts) {
        this.unchecked_posts = unchecked_posts;
    }
}
