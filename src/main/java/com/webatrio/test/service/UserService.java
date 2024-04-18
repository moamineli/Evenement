package com.webatrio.test.service;

import com.webatrio.test.models.User;

import java.util.Optional;

public interface UserService {


    User findUserById(Long id);
}
