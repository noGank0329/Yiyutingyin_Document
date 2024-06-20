package com.yiyu.mapper;

import com.yiyu.pojo.NewsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminAndUserNewsMapper {
//    获取所有的资讯
    @Select("select message_id,message_title,message_content,message_time,message_type from news_info")
    NewsInfo[] getAllNews();
}
