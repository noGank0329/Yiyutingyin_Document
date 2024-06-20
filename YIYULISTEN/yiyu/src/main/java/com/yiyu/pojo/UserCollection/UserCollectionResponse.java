package com.yiyu.pojo.UserCollection;

import com.yiyu.pojo.BlogPosts;
import lombok.Data;

import java.util.List;
@Data
public class UserCollectionResponse {
    private List<BlogPosts> collection;
    public UserCollectionResponse(List<BlogPosts> collection) {
        this.collection = collection;
    }
}
