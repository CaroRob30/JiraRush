package com.codegym.jira.bugtracking.attachment.to;

import com.codegym.jira.common.to.NamedTo;
import lombok.Getter;

@Getter
public class AttachmentTo extends NamedTo {
    public AttachmentTo(Long id, String name) {
        super(id, name);
    }
}
