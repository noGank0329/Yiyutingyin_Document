package com.yiyu.pojo.AdminUserNews;

import com.yiyu.pojo.AdminGotPosts.AdminGotPosts;
import com.yiyu.pojo.NewsInfo;
import lombok.Data;

import java.util.List;

@Data
public class AdminUserGetNews {
    private List<NewsInfo> message_list;
    public AdminUserGetNews(List<NewsInfo> message_list) {
        this.message_list = message_list;
    }
}
