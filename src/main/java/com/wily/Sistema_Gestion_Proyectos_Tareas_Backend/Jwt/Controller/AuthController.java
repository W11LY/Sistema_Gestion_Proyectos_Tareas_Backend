package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Controller;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Client.DtoClientRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Dtos.DtoAuthResponse;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Dtos.DtoLoginRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Services.AuthService;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wily/api/auth")
public class AuthController {

    final private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//    registro de client con password encriptada
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody DtoClientRequest request) {
        authService.register(request);
        return ResponseEntity.noContent().build();
    }

//    validacion de loging y generacion de token
    @PostMapping("/login")
    public ResponseEntity<DtoAuthResponse> login(@Valid @RequestBody DtoLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

//    validacion de token y expiracion
    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token no proporcionado");
        }

        String token = authHeader.substring(7);

        try {
            Client client = authService.validateTokenAndGetClient(token);
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}
