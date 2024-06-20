package com.yiyu.service;

import com.yiyu.pojo.AdminGotPosts.AdminGotPosts;

public interface AdminPosts {
//    审核帖子
    void checkPosts(String blog_id, Integer blog_status);
//获得所有未审核的帖子
    AdminGotPosts[] getUncheckedPosts();
}
