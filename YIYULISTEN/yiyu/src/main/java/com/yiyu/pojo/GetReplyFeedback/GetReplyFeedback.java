package com.yiyu.pojo.GetReplyFeedback;

import lombok.Data;

@Data
public class GetReplyFeedback {
    private int feedback_id;
    private String feedback;
    private String time;
    private String result;
    private String result_time;
    private String user_id;
}
