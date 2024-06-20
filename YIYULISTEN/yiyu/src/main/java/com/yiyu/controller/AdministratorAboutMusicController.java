package com.yiyu.controller;


import com.yiyu.pojo.Result;
import com.yiyu.service.AdministratorAboutMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/AdministratorAboutMusic")
public class AdministratorAboutMusicController {
    @Autowired
    private AdministratorAboutMusicService administratorAboutMusicService;

    @PostMapping("/AddMusic")    //管理员添加音乐
    public Result addMusic(String music_name,String music_style,int music_type,String music_link){
        administratorAboutMusicService.addMusic(music_name,music_style,music_type,music_link);
        return Result.success();
    }

    @PostMapping("/DeleteMusic")   //管理员删除音乐
    public Result deleteMusic(int music_id){
        administratorAboutMusicService.deleteMusic(music_id);
        return Result.success();
    }

    @PostMapping("/ModifyMusic")   //管理员修改音乐信息
    public Result updateMusic(int music_id,int music_type,String music_style,String music_name,String music_link){
        administratorAboutMusicService.modifyMusic(music_id,music_name,music_style,music_type,music_link);
        return Result.success();
    }

    @PostMapping("/HandleFeedback")   //管理员处理反馈
    public Result handleFeedback(String user_id,String content,String time,int feedback_id){
        administratorAboutMusicService.handleFeedback(user_id,content,time,feedback_id);
        return Result.success();
    }

}
