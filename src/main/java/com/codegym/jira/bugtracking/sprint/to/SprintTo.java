package com.codegym.jira.bugtracking.sprint.to;

import com.codegym.jira.common.HasCode;
import com.codegym.jira.common.to.CodeTo;
import com.codegym.jira.common.util.validation.Code;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SprintTo extends CodeTo implements HasCode {
    @Code
    String statusCode;
    @NotNull
    Long projectId;

    public SprintTo(Long id, String code, String statusCode, Long projectId) {
        super(id, code);
        this.statusCode = statusCode;
        this.projectId = projectId;
    }
}
