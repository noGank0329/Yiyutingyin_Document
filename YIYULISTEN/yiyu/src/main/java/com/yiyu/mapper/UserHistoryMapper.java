package com.yiyu.mapper;

import com.yiyu.pojo.GetAllMusic.GetAllMusic;
import com.yiyu.pojo.GetCollection.GetCollection;
import com.yiyu.pojo.GetFeedback.GetFeedback;
import com.yiyu.pojo.GetQuestionnaireHistory.GetQuestionnaireHistory;
import com.yiyu.pojo.GetReplyFeedback.GetReplyFeedback;
import com.yiyu.pojo.GetTestHistory.GetTestHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserHistoryMapper {
    @Select("select question,score,date from questionnaire_history where user_id=#{userId}")
    GetQuestionnaireHistory[] QuestionnaireHistory(String userId);

    @Select("select right_score,left_score,all_score,date from pure_tone_audiometry_history where user_id=#{userId}")
    GetTestHistory[] testHistory(String userId);

    @Select("select blog_id,blog_title,blog_content,blog_time,blog_comment_num,blog_collect_num from blog_posts where user_id=#{userId}")
    GetCollection[] collection(String userId);

    @Select("select feedback_id,feedback,time,result,result_time from feedback_info where user_id=#{userId}")
    GetFeedback[] feedback(String userId);

    @Select("select * from music_info ")
    GetAllMusic[] music();

    @Select("select feedback_id, user_id, feedback, time, result, result_time from feedback_info")
    GetReplyFeedback[] reply();
}
