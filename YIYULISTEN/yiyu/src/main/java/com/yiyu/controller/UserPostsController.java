package com.yiyu.controller;

import com.yiyu.pojo.BlogPosts;
import com.yiyu.pojo.Result;
import com.yiyu.pojo.UserCollection.UserCollectionResponse;
import com.yiyu.pojo.UserGotComment.UserGotComment;
import com.yiyu.pojo.UserGotComment.UserGotCommentResponse;
import com.yiyu.pojo.UserGotPersonalPost.UserGotPersonalPostResponse;
import com.yiyu.pojo.UserGotPosts.UserGotPosts;
import com.yiyu.pojo.UserGotPosts.UserGotPostsResponse;
import com.yiyu.service.UserPosts;
import com.yiyu.utils.blog_id_generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user_posts")
@CrossOrigin
public class UserPostsController {
    @Autowired
    private UserPosts userPosts;

    private static blog_id_generator idGenerator = new blog_id_generator(1, 1);

//    用户发帖
    @PostMapping("/post_blog")
    public Result userPost(String user_id,String blog_title,String blog_content, String blog_time)
    {
//        通过雪花算法生成帖子的ID
        String blog_id=idGenerator.nextId();
//        System.out.println(blog_id);
//        用户发帖
        userPosts.addPost(blog_id,user_id,blog_title,blog_content,blog_time);

        return Result.success();
    }

//    用户收藏帖子
    @PostMapping("/collection")
    public Result userAddCollection(String user_id,String blog_id)
    {
//        添加收藏
        userPosts.addCollection(user_id,blog_id);
        return Result.success();
    }

//    用户取消收藏
    @PostMapping("/delete_collection")
    public Result userDeleteCollection(String user_id,String blog_id)
    {
        userPosts.deleteCollection(user_id,blog_id);
        return Result.success();
    }

//    用户评论帖子
    @PostMapping("/comment")
    public Result userComment(String blog_id,String user_id,String comment_content, String comment_time)
    {
//        添加评论
        userPosts.addComment(blog_id,user_id,comment_content,comment_time);
        return Result.success();
    }

//    用户删除帖子
    @PostMapping("/delete")
    public Result userDelete(String user_id,String blog_id)
    {
//        删除帖子
        userPosts.deletePost(user_id,blog_id);
        return Result.success();
    }
//    用户查看审核通过的所有帖子
    @GetMapping("/get_all_post")
    public Result userGetAllPost()
    {
//        获取所有审核通过的帖子
        UserGotPosts posts[];
        posts = userPosts.GetAllPost();
        return Result.success(new UserGotPostsResponse(List.of(posts)));
    }

//    用户查看指定帖子的评论
    @GetMapping("/get_post_comment")
    public Result getPostComment(String blog_id)
    {
        UserGotComment comments[];
        comments = userPosts.GetAllComment(blog_id);
        return Result.success(new UserGotCommentResponse(List.of(comments)));
    }

//    用户查看自己所发的帖子
    @GetMapping("/get_personal_post")
    public Result getPersonalPost(String user_id)
    {
        BlogPosts posts[];
        posts = userPosts.getPersonalPost(user_id);
        System.out.println(Arrays.toString(posts));
        return Result.success(new UserGotPersonalPostResponse(List.of(posts)));
    }

    //    用户查看自己收藏的帖子
    @GetMapping("/get_personal_collections")
    public Result getPersonalCollections(String user_id)
    {
        BlogPosts collections[];
        collections = userPosts.getPersonalCollections(user_id);
        System.out.println(Arrays.toString(collections));
        return Result.success(new UserCollectionResponse(List.of(collections)));
    }

}



