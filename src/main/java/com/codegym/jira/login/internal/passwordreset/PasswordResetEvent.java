package com.codegym.jira.login.internal.passwordreset;

import com.codegym.jira.login.User;
import com.codegym.jira.common.AppEvent;

public record PasswordResetEvent(User user, String token) implements AppEvent {
}
