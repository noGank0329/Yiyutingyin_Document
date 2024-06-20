package com.yiyu.service.impl;

import com.yiyu.mapper.AdminPostMapper;
import com.yiyu.pojo.AdminGotPosts.AdminGotPosts;
import com.yiyu.service.AdminPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminPostsImpl implements AdminPosts {
    @Autowired
    private AdminPostMapper adminPostMapper;

    @Override
    public void checkPosts(String blog_id, Integer blog_status) {
        adminPostMapper.checkPosts(blog_id,blog_status);
    }

    @Override
    public AdminGotPosts[] getUncheckedPosts() {

        return adminPostMapper.getUncheckedPosts();
    }
}
