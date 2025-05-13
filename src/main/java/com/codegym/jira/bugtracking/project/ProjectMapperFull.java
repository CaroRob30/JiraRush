package com.codegym.jira.bugtracking.project;

import com.codegym.jira.common.BaseMapper;
import com.codegym.jira.common.TimestampMapper;
import com.codegym.jira.bugtracking.project.to.ProjectToFull;
import org.mapstruct.Mapper;

@Mapper(config = TimestampMapper.class)
public interface ProjectMapperFull extends BaseMapper<Project, ProjectToFull> {
}
