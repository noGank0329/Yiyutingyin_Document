package com.yiyu.service.impl;

import com.yiyu.mapper.AdministratorAboutMusicMapper;
import com.yiyu.service.AdministratorAboutMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorAboutMusicServiceImpl implements AdministratorAboutMusicService {
    @Autowired
    private AdministratorAboutMusicMapper administratorAboutMusicMapper;
    @Override
    public void addMusic(String musicName, String musicStyle, int musicType, String musicLink) {
        administratorAboutMusicMapper.addMusic(musicName,musicStyle,musicType,musicLink);
    }

    @Override
    public void deleteMusic(int musicId) {
        administratorAboutMusicMapper.deleteMusic(musicId);
    }

    @Override
    public void modifyMusic(int musicId, String musicName, String musicStyle, int musicType,String link) {
        administratorAboutMusicMapper.modifyMusic(musicId,musicName,musicStyle,musicType,link);
    }

    @Override
    public void handleFeedback(String userId, String content, String time, int feedbackId) {
        administratorAboutMusicMapper.handleFeedback(userId,content,time,feedbackId);
    }


}
