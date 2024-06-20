package com.yiyu.controller;

import com.yiyu.pojo.Result;
import com.yiyu.service.AdminNewsService;
import com.yiyu.utils.message_id_generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//管理员对资讯的增删改
@RestController
@RequestMapping("/admin_news")
@CrossOrigin
public class AdminNewsController {
    @Autowired
    AdminNewsService adminNewsService;
//    管理员添加资讯
    @PostMapping("/add_news")
    Result AddNews(String message_title,String message_content,Integer message_type, String message_time)
    {
//        雪花算法生成资讯的id
        message_id_generator idGenerator = new message_id_generator(1, 1);
        String message_id = idGenerator.nextId();
        adminNewsService.AddNews(message_id,message_title,message_content,message_type,message_time);
        return Result.success();

    }
//    管理员删除资讯
    @PostMapping("/delete_news")
    Result deleteNews(String message_id)
    {
        adminNewsService.deleteNews(message_id);
        return Result.success();
    }
//    管理员修改资讯
    @PostMapping("/modify_news")
    Result modifyNews(String message_id,String message_title,String message_content,String message_type)
    {
        adminNewsService.modifyNews(message_id,message_title, message_content, message_type);
        System.out.println(message_id);
        return Result.success();
    }


}
