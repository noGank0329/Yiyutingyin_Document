package com.yiyu.pojo;

import lombok.Data;

@Data
public class BlogComments {
  private String comment_id;
  private String blog_id;
  private String user_id;
  private String comment_content;
  private String comment_time;


}
