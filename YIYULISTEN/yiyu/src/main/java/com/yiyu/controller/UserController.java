package com.yiyu.controller;


import com.yiyu.pojo.Result;
import com.yiyu.pojo.UserInfo;
import com.yiyu.service.UserService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
//注册
    @PostMapping("/register")
    public Result register(String user_id, String user_name,String user_gender,String user_nickname, String user_password) {
        //查询用户
//        System.out.println(user_id + ", " + user_name + ", " + user_gender + ", " + user_nickname + ", " + user_password);
//        System.out.println("用户名："+user_name);
        UserInfo u = userService.findByUserID(user_id);
//        System.out.println(u);
        if (u == null) {
            //没有占用
            //注册
            userService.register(user_id,user_name,user_gender,user_nickname,user_password);
            return Result.success();
        } else {
           //占用
            return Result.error("用户ID已占用");

        }
    }

//登录
    @GetMapping("/login")
    public Result login(String user_id,String user_password) {
        UserInfo login_user = userService.findByUserIDAndPwd(user_id,user_password);
        System.out.println(login_user);
        if (login_user == null) {
            //ID或者密码错误
            return Result.error("ID或者密码错误");
        } else {
            //成功
            return Result.success(login_user);
        }

    }

//修改个人信息
    @PostMapping("/modify_personal_information")
    public Result modify_personal_information(String user_id,String user_nickname,String user_gender) {
//        UserInfo old_user_info = userService.findByUserID(user_id);
//        System.out.println(old_user_info);
//        old_user_info.setUser_nickname(user_nickname);
//        old_user_info.setUser_gender(user_gender);
//        old_user_info.setUser_gender(user_password);
        userService.modify_personal_information(user_id,user_nickname,user_gender);
        System.out.println(user_nickname);
        return Result.success();
    }
//    获取个人信息
    @GetMapping("/get_personal_information")
    public Result getPersonalInformation(String user_id)
    {
        UserInfo u = userService.get_personal_information(user_id);
        return Result.success(u);
    }
//  用户修改密码
    @PostMapping("/modify_password")
    public Result modifyPassword(String user_id,String user_password,String user_new_password)
    {
        UserInfo user = userService.findByUserIDAndPwd(user_id,user_password);
        if (user == null) {
            //ID或者密码错误
            return Result.error("旧密码验证错误");
        } else {
            //成功
            userService.modifyPassword(user_id,user_new_password);
            return Result.success();
        }
        //userService.modifyPassword(user_id,user_new_password);
        //return Result.success();
    }

}
