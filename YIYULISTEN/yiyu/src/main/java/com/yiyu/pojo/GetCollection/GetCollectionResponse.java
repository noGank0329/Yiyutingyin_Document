package com.yiyu.pojo.GetCollection;

import lombok.Data;

import java.util.List;

@Data
public class GetCollectionResponse {
    private List<GetCollection> collection;
    public GetCollectionResponse(List<GetCollection> collection){
        this.collection=collection;
    }
}
