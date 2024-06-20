package com.yiyu.pojo.GetFeedback;

import lombok.Data;

import java.util.List;

@Data
public class GetFeedbackResponse {
    private List<GetFeedback> feedback_list;
    public GetFeedbackResponse(List<GetFeedback> feedback_list){
        this.feedback_list=feedback_list;
    }
}
