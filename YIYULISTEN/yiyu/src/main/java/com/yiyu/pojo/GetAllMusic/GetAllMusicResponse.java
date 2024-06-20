package com.yiyu.pojo.GetAllMusic;

import lombok.Data;

import java.util.List;

@Data
public class GetAllMusicResponse {
    private List<GetAllMusic> music_list;
    public GetAllMusicResponse(List<GetAllMusic> music_list){
        this.music_list=music_list;
    }
}
