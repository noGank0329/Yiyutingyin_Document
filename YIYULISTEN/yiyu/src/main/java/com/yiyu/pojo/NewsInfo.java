package com.yiyu.pojo;

import lombok.Data;

@Data
public class NewsInfo {

  private String message_id;
  private String message_title;
  private String message_content;
  private String message_time;
  private Integer message_type;


}