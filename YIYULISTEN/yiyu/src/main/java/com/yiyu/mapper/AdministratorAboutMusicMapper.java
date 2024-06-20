package com.yiyu.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdministratorAboutMusicMapper {
    @Insert("insert into music_info(music_name,music_type,music_style,music_link)"+
    " values (#{musicName},#{musicType},#{musicStyle},#{musicLink})")
    void addMusic(String musicName, String musicStyle, int musicType, String musicLink);

    @Delete("delete from music_info where music_id=#{musicId}")
    void deleteMusic(int musicId);

    @Update("update music_info set music_name=#{musicName} ,music_link=#{link}, music_style=#{musicStyle} , music_type=#{musicType}"+
    " where music_id=#{musicId}")
    void modifyMusic(int musicId, String musicName, String musicStyle, int musicType,String link);

    @Update(("update feedback_info set result=#{content},result_time=#{time}"+
            " where feedback_id=#{feedbackId}"))
    void handleFeedback(String userId, String content, String time, int feedbackId);
}
