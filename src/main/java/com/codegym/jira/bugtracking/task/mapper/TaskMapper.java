package com.codegym.jira.bugtracking.task.mapper;

import com.codegym.jira.common.BaseMapper;
import com.codegym.jira.bugtracking.task.Task;
import com.codegym.jira.bugtracking.task.to.TaskTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends BaseMapper<Task, TaskTo> {

    @Override
    TaskTo toTo(Task task);
}
