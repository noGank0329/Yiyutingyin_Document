package com.yiyu.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminNewsMapper {
//管理员增加资讯
    @Insert("insert into news_info(message_id,message_title,message_content,message_type,message_time) "+
            "values (#{message_id},#{message_title},#{message_content},#{message_type},#{message_time})")
    void AddNews(String message_id, String message_title, String message_content, Integer message_type, String message_time);
//管理员删除资讯
    @Delete("delete from news_info where message_id = #{message_id}")
    void deleteNews(String message_id);
//管理员修改咨询
    @Update("update news_info set message_title=#{message_title},message_content=#{message_content},message_type=#{message_type} where message_id=#{message_id}")
    void modifyNews(String message_id,String message_title, String message_content, String message_type);
}
