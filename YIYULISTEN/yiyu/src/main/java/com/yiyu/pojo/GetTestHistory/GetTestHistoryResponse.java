package com.yiyu.pojo.GetTestHistory;

import lombok.Data;

import java.util.List;

@Data
public class GetTestHistoryResponse {
    private List<GetTestHistory> test_history;
    public GetTestHistoryResponse(List<GetTestHistory> test_history){
        this.test_history=test_history;
    }
}
