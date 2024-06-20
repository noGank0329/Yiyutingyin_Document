package com.yiyu.service.impl;

import com.yiyu.mapper.UserMapper;
import com.yiyu.pojo.UserInfo;
import com.yiyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserInfo findByUserID(String user_id) {
        UserInfo u =userMapper.findByUserID(user_id);
        return u;
    }

    @Override
    public void register(String user_id, String user_name,String user_gender,String user_nickname, String user_password) {
//        添加
        userMapper.add(user_id, user_name,user_gender,user_nickname, user_password);
    }

    @Override
    public UserInfo findByUserIDAndPwd(String user_id, String user_password) {
        UserInfo login_user = userMapper.findByUserIDAndPwd(user_id,user_password);
        return login_user;
    }

    @Override
    public void modify_personal_information(String user_id, String user_nickname, String user_gender) {
        userMapper.modify_personal_information(user_id,user_nickname,user_gender);
    }

    @Override
    public UserInfo get_personal_information(String user_id) {
        UserInfo u = userMapper.get_personal_information(user_id);
        return u;
    }

    @Override
    public void modifyPassword(String user_id, String user_password) {
        userMapper.modifyPassword(user_id,user_password);
    }
}
