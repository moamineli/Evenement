package com.webatrio.test.service;

import com.webatrio.test.payload.request.LoginRequest;
import com.webatrio.test.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signUpRequest);
}
