package com.yiyu.controller;

import com.yiyu.pojo.MusicInfo;
import com.yiyu.pojo.Result;
import com.yiyu.service.MusicTestAndCureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController     //控制器中所有的处理器方法的返回值都要直接写入响应体中
@RequestMapping("/MusicTestAndCure")    //此处是请求路径
public class MusicTestAndCureController {
    @Autowired
    private MusicTestAndCureService musicTestAndCureService;

    //private static int ID;      //静态变量来代替appraisal_id
    //private static int testID;   //静态变量来表示test_id

    @PostMapping("/Questionnaire")      //完成问卷评估+评分，直接向数据库中插入即可
    public Result Questionnaire(String user_id,String date,String question,int score){
        //要设置一个静态变量来代替appraisal_id，向数据库插入的时候插入该静态变量即可
        musicTestAndCureService.addQuestionnaire(user_id,date,question,score);
        //ID++;
        return Result.success();
    }

    @PostMapping("/Test")       //用户进行听力测试，向数据库中插入听力测试记录
    public Result Test(String user_id,String right_score,String left_score,String date,int all_score){
        musicTestAndCureService.addTest(user_id,right_score,left_score,date,all_score);
        //testID++;
        return Result.success();
    }
    /*       该接口已废除
    @GetMapping("/Therapy")       //用户进行治疗，即获取推荐曲库一级类别
    public Result Therapy(String user_id){
        MusicInfo m=musicTestAndCureService.getMusicType(user_id);
        return Result.success(m);
    }*/
    @PostMapping("/Feedback")      //用户进行反馈，向数据库中插入反馈信息
    public Result Feedback(String user_id,String feedback,String time,int feedback_type){
        musicTestAndCureService.addFeedback(user_id,feedback,time,feedback_type);
        return Result.success();
    }
}
