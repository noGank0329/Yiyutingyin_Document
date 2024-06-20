package com.yiyu.controller;


import com.yiyu.pojo.AdminGotPosts.AdminGotPosts;
import com.yiyu.pojo.AdminGotPosts.AdminGotPostsResponse;
import com.yiyu.pojo.Result;
import com.yiyu.pojo.UserGotPosts.UserGotPosts;
import com.yiyu.service.AdminPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin_posts")
@CrossOrigin
public class AdminPostsController {
    @Autowired
    private AdminPosts adminPosts;
    //管理员审核帖子
    @PostMapping("/check_posts")
    public Result adminCheckPosts(String blog_id,Integer blog_status)
    {
    //审核帖子（修改blog_status）
        adminPosts.checkPosts(blog_id,blog_status);
        return Result.success();
    }
    //管理员查看所有待审核的帖子
    @GetMapping("/get_unchecked_posts")
    public Result getUncheckedPosts()
    {
        // 获取所有待审核的帖子
        AdminGotPosts posts[];
        posts = adminPosts.getUncheckedPosts();
        return Result.success(new AdminGotPostsResponse(List.of(posts)));
    }
}
