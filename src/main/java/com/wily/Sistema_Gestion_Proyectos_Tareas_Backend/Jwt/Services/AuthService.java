package com.wily.task_manager.Jwt.Services;

import com.wily.task_manager.Enums.Role;
import com.wily.task_manager.Jwt.Dtos.DtoLoginRequest;
import com.wily.task_manager.Jwt.Dtos.DtoRegisterRequest;
import com.wily.task_manager.Jwt.Dtos.DtoAuthResponse;
import com.wily.task_manager.Model.User;
import com.wily.task_manager.Repository.iUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {

    private final iUserRepository iUserRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(iUserRepository iUserRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public DtoAuthResponse register(DtoRegisterRequest request) {
        if (iUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        User user = new User();
        user.setUsername(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        iUserRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), user);
        return new DtoAuthResponse(token);
    }

    public DtoAuthResponse login(DtoLoginRequest request) {
        User user = iUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("Email o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        String token = jwtService.generateToken(user.getEmail(), user);
        return new DtoAuthResponse(token);
    }
}
