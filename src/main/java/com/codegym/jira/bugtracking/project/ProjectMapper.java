package com.codegym.jira.bugtracking.project;

import com.codegym.jira.common.BaseMapper;
import com.codegym.jira.common.TimestampMapper;
import com.codegym.jira.bugtracking.project.to.ProjectTo;
import org.mapstruct.Mapper;

@Mapper(config = TimestampMapper.class)
public interface ProjectMapper extends BaseMapper<Project, ProjectTo> {
}
