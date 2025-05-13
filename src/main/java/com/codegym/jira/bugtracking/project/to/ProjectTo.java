package com.codegym.jira.bugtracking.project.to;

import com.codegym.jira.common.to.TitleTo;
import com.codegym.jira.common.util.validation.Code;
import com.codegym.jira.common.util.validation.Description;
import lombok.Getter;

@Getter
public class ProjectTo extends TitleTo {
    @Description
    String description;

    @Code
    String typeCode;

    Long parentId;

    public ProjectTo(Long id, String code, String title, String description, String typeCode, Long parentId) {
        super(id, code, title);
        this.description = description;
        this.typeCode = typeCode;
        this.parentId = parentId;
    }
}
