package com.codegym.jira.bugtracking.attachment;

import com.codegym.jira.common.BaseMapper;
import com.codegym.jira.common.TimestampMapper;
import com.codegym.jira.bugtracking.attachment.to.AttachmentTo;
import org.mapstruct.Mapper;

@Mapper(config = TimestampMapper.class)
public interface AttachmentMapper extends BaseMapper<Attachment, AttachmentTo> {
}
