package com.yiyu.pojo;

import lombok.Data;

@Data
public class FeedbackInfo {

  private String feedbackId;
  private String userId;
  private String feedback;
  private Integer feedbackType;
  private java.sql.Timestamp time;



}
