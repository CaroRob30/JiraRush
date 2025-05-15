package com.codegym.jira.login.internal;

import com.codegym.jira.login.User;
import com.codegym.jira.login.UserTo;
import com.codegym.jira.common.BaseHandler;
import org.springframework.stereotype.Component;

import java.util.function.BinaryOperator;

@Component
public class UserHandler extends BaseHandler<User, UserTo, UserRepository, UserMapper> {
    public UserHandler(UserRepository repository, UserMapper mapper) {
        super(repository, mapper,
                UsersUtil::prepareForCreate,
                (BinaryOperator<User>) (user, dbUser) -> UsersUtil.prepareForUpdate(user, dbUser.getPassword()));
    }
}
