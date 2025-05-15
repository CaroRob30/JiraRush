package com.codegym.jira.bugtracking.sprint;

import com.codegym.jira.common.BaseMapper;
import com.codegym.jira.common.TimestampMapper;
import com.codegym.jira.bugtracking.sprint.to.SprintToFull;
import org.mapstruct.Mapper;

@Mapper(config = TimestampMapper.class)
public interface SprintMapperFull extends BaseMapper<Sprint, SprintToFull> {
}
