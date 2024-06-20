package com.yiyu.pojo.GetReplyFeedback;

import com.yiyu.pojo.GetFeedback.GetFeedback;
import lombok.Data;

import java.util.List;

@Data
public class GetReplyFeedbackResponse {
    private List<GetReplyFeedback> feedback_list;
    public GetReplyFeedbackResponse(List<GetReplyFeedback> feedback_list){
        this.feedback_list=feedback_list;
    }
}
