package com.yiyu.pojo;

import lombok.Data;

//编译阶段为实体类自动生成setter getter toString 等方法
@Data
public class UserInfo {

  private String user_id;
  private String user_name;
  private String user_gender;
  private String user_nickname;
  private Integer user_type;
  private String user_password;
}
