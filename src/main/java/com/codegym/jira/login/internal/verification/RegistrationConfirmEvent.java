package com.codegym.jira.login.internal.verification;

import com.codegym.jira.common.AppEvent;
import com.codegym.jira.login.UserTo;

public record RegistrationConfirmEvent(UserTo userto, String token) implements AppEvent {
}
