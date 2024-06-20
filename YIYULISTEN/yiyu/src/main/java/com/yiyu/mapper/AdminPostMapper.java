package com.yiyu.mapper;

import com.yiyu.pojo.AdminGotPosts.AdminGotPosts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminPostMapper {
//审核（更新帖子的status）
    @Update("update blog_posts set blog_status=#{blog_status} where blog_id=#{blog_id}")
    void checkPosts(String blog_id, Integer blog_status);
//获取所有为审核的帖子
@Select("select blog_id,user_id,user_nickname,blog_id,blog_title,blog_content,blog_collect_num,blog_comment_num,blog_time"+
        " from blog_posts natural join user_info where blog_status = 0")
    AdminGotPosts[] getUncheckedPosts();
}
