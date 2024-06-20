package com.yiyu.service;

import com.yiyu.pojo.UserInfo;

public interface UserService {
//根据用户ID查询用户
    UserInfo findByUserID(String userID);
//注册
    void register(String user_id, String user_name,String user_gender,String user_nickname, String user_password);
//登录（根据用户ID和密码查找用户）
    UserInfo findByUserIDAndPwd(String user_id, String user_password);
//   修改用户个人信息（昵称，性别，密码）
    void modify_personal_information(String user_id, String user_nickname, String user_gender);
//获取用户的信息
    UserInfo  get_personal_information(String user_id);
//用户修改密码
    void modifyPassword(String user_id, String user_password);
}
