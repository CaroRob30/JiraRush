package com.codegym.jira.bugtracking.task.mapper;

import com.codegym.jira.common.BaseMapper;
import com.codegym.jira.common.TimestampMapper;
import com.codegym.jira.bugtracking.task.Task;
import com.codegym.jira.bugtracking.task.to.TaskToFull;
import org.mapstruct.Mapper;

@Mapper(config = TimestampMapper.class)
public interface TaskFullMapper extends BaseMapper<Task, TaskToFull> {

    @Override
    TaskToFull toTo(Task task);
}
