package com.yiyu.pojo.UserGotComment;

import com.yiyu.pojo.AdminGotPosts.AdminGotPosts;
import lombok.Data;

import java.util.List;

@Data
public class UserGotCommentResponse {
    private List<UserGotComment> all_comment;
    public UserGotCommentResponse(List<UserGotComment> all_comment) {
        this.all_comment = all_comment;
    }
}
