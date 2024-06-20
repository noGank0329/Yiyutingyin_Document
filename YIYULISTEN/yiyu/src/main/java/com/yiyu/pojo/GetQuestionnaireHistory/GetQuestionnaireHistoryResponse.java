package com.yiyu.pojo.GetQuestionnaireHistory;

import lombok.Data;

import java.util.List;
@Data
public class GetQuestionnaireHistoryResponse {
    private List<GetQuestionnaireHistory> questionnaire_history;
    public GetQuestionnaireHistoryResponse(List<GetQuestionnaireHistory> questionnaireHistory){
        this.questionnaire_history=questionnaireHistory;
    }
}
