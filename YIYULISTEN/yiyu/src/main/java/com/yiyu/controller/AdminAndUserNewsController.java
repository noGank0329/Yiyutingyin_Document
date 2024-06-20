package com.yiyu.controller;

import com.yiyu.pojo.AdminUserNews.AdminUserGetNews;
import com.yiyu.pojo.NewsInfo;
import com.yiyu.service.AdminAndUserNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yiyu.pojo.Result;

import java.util.Arrays;
import java.util.List;

//用户和资讯相关
@RestController
@RequestMapping("/admin_user_news")
@CrossOrigin
public class AdminAndUserNewsController {
    @Autowired
    private AdminAndUserNews adminAndUserNews;

    @GetMapping("/get_all_news")
    public Result getAllNews()
    {
        NewsInfo[] newsInfo = adminAndUserNews.getAllNews();
        AdminUserGetNews adminUserGetNews= new AdminUserGetNews(List.of(newsInfo));
        return Result.success(adminUserGetNews);
    }



}
