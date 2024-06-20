package com.yiyu.service;

public interface AdministratorAboutMusicService {
    void addMusic(String musicName, String musicStyle, int musicType, String musicLink);

    void deleteMusic(int musicId);

    void modifyMusic(int musicId, String musicName, String musicStyle, int musicType,String link);

    void handleFeedback(String userId, String content, String time, int feedbackId);


}
