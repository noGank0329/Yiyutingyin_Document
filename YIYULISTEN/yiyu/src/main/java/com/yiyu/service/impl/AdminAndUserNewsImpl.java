package com.yiyu.service.impl;

import com.yiyu.mapper.AdminAndUserNewsMapper;
import com.yiyu.pojo.NewsInfo;
import com.yiyu.service.AdminAndUserNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminAndUserNewsImpl implements AdminAndUserNews {
    @Autowired
    AdminAndUserNewsMapper adminAndUserNewsMapper;
//    用户和管理员获取所有的资讯
    @Override
    public NewsInfo[] getAllNews() {
        System.out.println(Arrays.toString(adminAndUserNewsMapper.getAllNews()));
        NewsInfo[] newsInfo=adminAndUserNewsMapper.getAllNews();
//        System.out.println(Arrays.toString(newsInfo));
        return newsInfo;
    }
}
