package com.wily.task_manager.Jwt.Controller;

import com.wily.task_manager.Jwt.Dtos.DtoLoginRequest;
import com.wily.task_manager.Jwt.Dtos.DtoRegisterRequest;
import com.wily.task_manager.Jwt.Dtos.DtoAuthResponse;
import com.wily.task_manager.Jwt.Services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wily/api/auth")
public class AuthController {

    final private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<DtoAuthResponse> register(@Valid @RequestBody DtoRegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<DtoAuthResponse> login(@Valid @RequestBody DtoLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
