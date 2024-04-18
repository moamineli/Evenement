package com.webatrio.test.serviceImpl;

import com.webatrio.test.models.User;
import com.webatrio.test.repository.UserRepository;
import com.webatrio.test.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {

    private final UserRepository repository;


    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public User findUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
