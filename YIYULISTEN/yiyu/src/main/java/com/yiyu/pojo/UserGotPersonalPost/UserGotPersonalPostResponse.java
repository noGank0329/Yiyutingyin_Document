package com.yiyu.pojo.UserGotPersonalPost;

import com.yiyu.pojo.BlogPosts;
import com.yiyu.pojo.UserGotComment.UserGotComment;
import lombok.Data;

import java.util.List;
//用于返回用户查询自己所发的帖子
@Data
public class UserGotPersonalPostResponse {
    private List<BlogPosts> personal_post;
    public UserGotPersonalPostResponse(List<BlogPosts> personal_post) {
        this.personal_post = personal_post;
    }
}
