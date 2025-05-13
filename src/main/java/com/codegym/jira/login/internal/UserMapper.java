package com.codegym.jira.login.internal;

import com.codegym.jira.login.Role;
import com.codegym.jira.login.User;
import com.codegym.jira.login.UserTo;
import com.codegym.jira.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.EnumSet;

@Mapper(componentModel = "spring", imports = {EnumSet.class, Role.class})
public interface UserMapper extends BaseMapper<User, UserTo> {
    @Override
    @Mapping(target = "roles", expression = "java(EnumSet.of(Role.DEV))")
    User toEntity(UserTo to);

    @Override
    @Mapping(target = "password", ignore = true)
    User updateFromTo(UserTo to, @MappingTarget User entity);
}
