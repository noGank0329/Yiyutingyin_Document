package com.yiyu.service.impl;

import com.yiyu.mapper.UserPostMapper;
import com.yiyu.pojo.BlogPosts;
import com.yiyu.pojo.UserGotComment.UserGotComment;
import com.yiyu.pojo.UserGotPosts.UserGotPosts;
import com.yiyu.service.UserPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPostsImpl implements UserPosts {
    @Autowired
    private UserPostMapper userPostsMapper;
    @Override
    public void addPost(String blog_id,String user_id, String blog_tile, String blog_content, String blog_time) {
//        发帖
        userPostsMapper.addPost(blog_id,user_id,blog_tile,blog_content,blog_time);
    }

    @Override
    public void addCollection(String user_id, String blog_id) {
//        添加收藏
        userPostsMapper.addCollection(user_id,blog_id);
//        对应帖子的收藏数增加
        userPostsMapper.incrementCollectCount(blog_id);
    }

    @Override
    public void addComment(String blog_id, String user_id, String comment_content, String comment_time) {
//        添加评论
        userPostsMapper.addComment(blog_id,user_id,comment_content,comment_time);
//        对应帖子的评论数增加
        userPostsMapper.incrementCommentCount(blog_id);
    }

    @Override
    public void deletePost(String user_id, String blog_id) {
//        删除帖子
        userPostsMapper.deletePost(user_id,blog_id);
    }

    @Override
    public UserGotPosts[] GetAllPost() {
//        获取所有审核通过的帖子
        UserGotPosts[] u ;
        u = userPostsMapper.GetAllPost();
        return u;
    }
//获取指定帖子的评论
    @Override
    public UserGotComment[] GetAllComment(String blog_id) {
        UserGotComment[] u;
        u = userPostsMapper.GetAllComment(blog_id);
        return u;
    }
//获取自己所发的帖子
    @Override
    public BlogPosts[] getPersonalPost(String user_id) {
        BlogPosts[] blogPosts;
        blogPosts = userPostsMapper.getPersonalPost(user_id);
        return blogPosts;
    }
//获取收藏
    @Override
    public BlogPosts[] getPersonalCollections(String user_id) {
        BlogPosts[] Collections;
        Collections = userPostsMapper.getPersonalCollections(user_id);
        return Collections;

    }
//取消收藏
    @Override
    public void deleteCollection(String user_id, String blog_id) {
        userPostsMapper.deleteCollection(user_id,blog_id);
        userPostsMapper.decreaseCollectCount(blog_id);
    }

}
