package com.codegym.jira.bugtracking.sprint;

import com.codegym.jira.common.BaseMapper;
import com.codegym.jira.common.TimestampMapper;
import com.codegym.jira.common.error.DataConflictException;
import com.codegym.jira.bugtracking.sprint.to.SprintTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = TimestampMapper.class)
public interface SprintMapper extends BaseMapper<Sprint, SprintTo> {
    static long checkProjectBelong(long projectId, Sprint dbSprint) {
        if (projectId != dbSprint.getProjectId())
            throw new DataConflictException("Sprint " + dbSprint.id() + " doesn't belong to Project " + projectId);
        return projectId;
    }

    @Override
    @Mapping(target = "projectId", expression = "java(SprintMapper.checkProjectBelong(sprintTo.getProjectId(), sprint))")
    Sprint updateFromTo(SprintTo sprintTo, @MappingTarget Sprint sprint);
}
