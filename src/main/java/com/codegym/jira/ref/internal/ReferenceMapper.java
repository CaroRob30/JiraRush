package com.codegym.jira.ref.internal;

import com.codegym.jira.ref.RefTo;
import com.codegym.jira.common.BaseMapper;
import com.codegym.jira.common.TimestampMapper;
import org.mapstruct.Mapper;

@Mapper(config = TimestampMapper.class)
public interface ReferenceMapper extends BaseMapper<Reference, RefTo> {
}
