package com.codegym.jira.mail.internal;

import com.codegym.jira.common.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MailCaseRepository extends BaseRepository<MailCase> {
}
