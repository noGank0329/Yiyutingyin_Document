package com.yiyu.mapper;

import com.yiyu.pojo.BlogPosts;
import com.yiyu.pojo.UserGotComment.UserGotComment;
import com.yiyu.pojo.UserGotPosts.UserGotPosts;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserPostMapper {
//添加帖子
    @Insert("insert into blog_posts(blog_id,user_id, blog_title, blog_content, blog_time) " +
            "values (#{blog_id},#{user_id}, #{blog_title}, #{blog_content}, #{blog_time})")
    void addPost(String blog_id,String user_id, String blog_title, String blog_content, String blog_time);
//对帖子添加收藏
    @Insert("insert into user_collections(user_id,blog_id) values (#{user_id},#{blog_id})")
    void addCollection(String user_id, String blog_id);
//对帖子添加评论
    @Insert("insert into blog_comments(blog_id,user_id,comment_content,comment_time)" +
            "values (#{blog_id},#{user_id},#{comment_content},#{comment_time})")
    void addComment(String blog_id, String user_id, String comment_content, String comment_time);
    // 更新帖子评论数
    @Update("update blog_posts set blog_comment_num = blog_comment_num + 1 where blog_id = #{blog_id}")
    void incrementCommentCount(String blog_id);

    // 更新帖子收藏数
    @Update("update blog_posts set blog_collect_num = blog_collect_num + 1 where blog_id = #{blog_id}")
    void incrementCollectCount(String blog_id);

//删除帖子
    @Delete("delete from blog_posts where blog_id=#{blog_id} and user_id=#{user_id}")
    void deletePost(String user_id, String blog_id);

//获取所有审核通过的帖子
    @Select("select user_nickname,blog_id,blog_title,blog_content,blog_collect_num,blog_comment_num,blog_time"+
            " from blog_posts natural join user_info where blog_status = 1")
    UserGotPosts[]GetAllPost();
//获取指定帖子的所有评论
    @Select("select user_nickname,comment_content,comment_time "
            +" from blog_comments natural join user_info "+"where blog_id=#{blog_id}")
    UserGotComment[] GetAllComment(String blog_id);
//获取自己所发的所有帖子
    @Select("select blog_id,blog_title,blog_content, blog_collect_num, blog_comment_num, blog_time from blog_posts where user_id=#{user_id}")
    BlogPosts[] getPersonalPost(String user_id);
//查看收藏帖子
    @Select("select * from blog_posts JOIN user_collections ON blog_posts.blog_id = user_collections.blog_id where user_collections.user_id=#{user_id};")
    BlogPosts[] getPersonalCollections(String user_id);
//取消收藏
    @Delete("delete from user_collections where blog_id=#{blog_id} and user_id=#{user_id}")
    void deleteCollection(String user_id, String blog_id);

    // 减少收藏数目
    @Update("update blog_posts set blog_collect_num = blog_collect_num - 1 where blog_id = #{blog_id}")
    void decreaseCollectCount(String blog_id);
}
