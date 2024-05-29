package com.webatrio.test.controllers;


import com.webatrio.test.payload.request.LoginRequest;
import com.webatrio.test.payload.request.SignupRequest;
import com.webatrio.test.service.AuthService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(value = "testWebatrioUser", description = "User")
@RequestMapping("/api/auth")
public class AuthController {
private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	return authService.authenticateUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

return authService.registerUser(signUpRequest);
}

}
