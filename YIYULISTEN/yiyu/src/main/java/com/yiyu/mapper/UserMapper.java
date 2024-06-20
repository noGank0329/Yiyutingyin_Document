package com.yiyu.mapper;

import com.yiyu.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("select * from user_info where user_id=#{user_id}")
    UserInfo findByUserID(String user_id);
//注册的插入用户
    @Insert("insert into user_info(user_id,user_name,user_gender,user_nickname,user_password)" +
            "values(#{user_id},#{user_name},#{user_gender},#{user_nickname},#{user_password})")
    void add(String user_id, String user_name, String user_gender, String user_nickname, String user_password);
//根据用户的ID和密码来验证登录
    @Select("select * from user_info where user_id=#{user_id} and user_password = #{user_password}")
    UserInfo findByUserIDAndPwd(String user_id, String user_password);
//更新用户的信息
    @Update("UPDATE user_info  SET user_nickname = #{user_nickname}, user_gender = #{user_gender}"+
            "WHERE user_id = #{user_id}")
    void modify_personal_information(String user_id, String user_nickname,String user_gender);
//返回个人信息
    @Select("select user_name,user_gender,user_nickname from user_info where user_id=#{user_id}")
    UserInfo get_personal_information(String user_id);
//修改密码
@Update("UPDATE user_info SET user_password = #{user_password} WHERE user_id = #{user_id}")
void modifyPassword(String user_id, String user_password);
}
