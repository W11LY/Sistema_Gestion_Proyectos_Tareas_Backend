package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Services;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Client.DtoClientRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Dtos.DtoAuthResponse;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Dtos.DtoLoginRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iClientRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.ServicesImpl.CurrentClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Service
public class AuthService {

//    inyeccion por constructor repositorio de client, uso de curren client y services de jwt y password encoder
    private final iClientRepository clientRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CurrentClientService currentClientService;

    public AuthService(iClientRepository clientRepository, JwtService jwtService, PasswordEncoder passwordEncoder, CurrentClientService currentClientService) {
        this.clientRepository = clientRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.currentClientService = currentClientService;
    }

//    validacion de unico correo para la creacion dde client y encriptado de password
    public DtoAuthResponse register(DtoClientRequest dtoClientRequest) {
        if (clientRepository.findByEmail(dtoClientRequest.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está en uso");
        }

        dtoClientRequest.setPassword(passwordEncoder.encode(dtoClientRequest.getPassword()));
        clientRepository.save(toEntity(dtoClientRequest));
        String token = jwtService.generateToken(dtoClientRequest.getEmail());
        return new DtoAuthResponse(token);

    }

//    validacion de credenciales email y password de loging y retorna token
    public DtoAuthResponse login(DtoLoginRequest dtoLoginRequest) {
        Client client = clientRepository.findByEmail(dtoLoginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email o Contraseña incorrectos"));

        if (!passwordEncoder.matches(dtoLoginRequest.getPassword(), client.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email o Contraseña incorrectos");
        }

        String token = jwtService.generateToken(dtoLoginRequest.getEmail());
        return new DtoAuthResponse(token);
    }

//    validacion de token y estructura existencia de email, verificacion de email en db, expiracion, validae de token
    public Client validateTokenAndGetClient(String token) {
        String email = jwtService.extractEmail(token);
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token sin email");
        }

        if (!jwtService.isTokenValid(token, email)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido o expirado");
        }

        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cliente no encontrado"));
    }

//    mappeo de dto a entity usado en create client
    private Client toEntity(DtoClientRequest dtoClientRequest) {
        Client client = new Client();
        client.setNames(dtoClientRequest.getNames());
        client.setLastnames(dtoClientRequest.getLastnames());
        client.setPhone(dtoClientRequest.getPhone());
        client.setEmail(dtoClientRequest.getEmail());
        client.setPassword(dtoClientRequest.getPassword());
        return client;
    }
}
