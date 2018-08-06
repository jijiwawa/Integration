package com.runner;

import com.forums.domain.User;

public interface UserMapper {

    int isUserNameExist(String username);

    int addUser(User user);

    User getUserByUserName(String username);
}