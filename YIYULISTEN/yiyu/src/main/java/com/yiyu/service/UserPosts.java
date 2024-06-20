package com.yiyu.service;

import com.yiyu.pojo.BlogPosts;
import com.yiyu.pojo.UserGotComment.UserGotComment;
import com.yiyu.pojo.UserGotPosts.UserGotPosts;

public interface UserPosts {
//    用户发帖
    void addPost(String blog_id,String user_id, String blog_tile, String blog_content, String blog_time);
//用户添加收藏
    void addCollection(String user_id, String blog_id);
//用户添加评论
    void addComment(String blog_id, String user_id, String comment_content, String comment_time);
//用户删除帖子
    void deletePost(String user_id, String blog_id);
//用户获取所有审核通过的帖子
    UserGotPosts[] GetAllPost();
//    用户获取指定帖子的所有评论
    UserGotComment[] GetAllComment(String blog_id);
//用户查看自己所发的所有帖子
    BlogPosts[] getPersonalPost(String user_id);
//用户查看自己的收藏帖子
    BlogPosts[] getPersonalCollections(String user_id);
//用户取消收藏
    void deleteCollection(String user_id, String blog_id);
}
