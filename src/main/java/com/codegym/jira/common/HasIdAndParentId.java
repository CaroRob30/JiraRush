package com.codegym.jira.common;

public interface HasIdAndParentId extends HasId {
    Long getParentId();
}
