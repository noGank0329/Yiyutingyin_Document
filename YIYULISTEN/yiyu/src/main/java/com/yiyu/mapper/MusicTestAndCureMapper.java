package com.yiyu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusicTestAndCureMapper {
    //向数据库中插入问卷历史
    @Insert("insert into questionnaire_history(user_id,question,score,date)"+
    " values(#{userId},#{question},#{score},#{date})")
    void addQuestionnaire(String userId, String date, String question, int score);

    //向数据库中插入听力测试记录
    @Insert("insert into pure_tone_audiometry_history(user_id, all_score, left_score, right_score, date) "+
    " values (#{userId},#{allScore},#{leftScore},#{rightScore},#{date})")
    void addTest(String userId, String rightScore, String leftScore, String date, int allScore);

    //向数据库中插入反馈记录
    @Insert("insert into feedback_info(user_id,feedback,feedback_type,time)"+
    " values(#{userId},#{feedback},#{feedbackType},#{time})")
    void addFeedback(String userId, String feedback, String time, int feedbackType);

    /*//从数据库中选择该用户的一级乐库类型      接口已废除
    @Select("select music_type from music_info,user_info where user_id=#{userId}")
    MusicInfo getMusicType(String userId);*/
}
