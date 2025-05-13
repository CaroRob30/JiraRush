package com.codegym.jira.bugtracking.task;

import com.codegym.jira.common.HasId;

public interface HasAuthorId extends HasId {
    Long getAuthorId();
}
