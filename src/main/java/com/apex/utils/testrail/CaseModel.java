package com.apex.utils.testrail;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class CaseModel {
    @JsonAlias("case_id")
    int caseId;

    @JsonAlias("status_id")
    int statusId;

    @JsonAlias("comment")
    String comment;
}