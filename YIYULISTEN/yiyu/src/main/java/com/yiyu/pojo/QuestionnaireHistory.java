package com.yiyu.pojo;

import lombok.Data;

@Data
public class QuestionnaireHistory {

  private Integer appraisalId;
  private String userId;
  private String question;
  private Integer score;
  private String date;



}
