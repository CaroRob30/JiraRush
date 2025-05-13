package com.codegym.jira.bugtracking.sprint.to;

import com.codegym.jira.common.to.CodeTo;
import lombok.Value;

@Value
public class SprintToFull extends SprintTo {
    CodeTo project;

    public SprintToFull(Long id, String code, String statusCode, CodeTo project) {
        super(id, code, statusCode, project.getId());
        this.project = project;
    }
}
