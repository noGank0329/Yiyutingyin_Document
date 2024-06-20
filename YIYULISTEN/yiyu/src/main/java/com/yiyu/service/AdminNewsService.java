package com.yiyu.service;

public interface AdminNewsService {
    void AddNews(String message_id, String message_title, String message_content, Integer message_type, String message_time);

    void deleteNews(String message_id);
    void modifyNews(String message_id,String message_title, String message_content, String message_type);
}
