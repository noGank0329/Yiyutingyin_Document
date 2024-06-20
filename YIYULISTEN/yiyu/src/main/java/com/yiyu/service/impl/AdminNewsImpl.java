package com.yiyu.service.impl;

import com.yiyu.mapper.AdminNewsMapper;
import com.yiyu.service.AdminNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminNewsImpl implements AdminNewsService {
    @Autowired
    AdminNewsMapper adminNewsMapper;
//管理员添加资讯
    @Override
    public void AddNews(String message_id, String message_title, String message_content, Integer message_type, String message_time) {
        adminNewsMapper.AddNews(message_id,message_title,message_content,message_type,message_time);
    }
//管理员删除资讯
    @Override
    public void deleteNews(String message_id) {
        adminNewsMapper.deleteNews(message_id);

    }
//管理员修改资讯
    @Override
    public void modifyNews(String message_id, String message_title, String message_content, String message_type) {
        adminNewsMapper.modifyNews(message_id,message_title,  message_content,  message_type);
    }
}
