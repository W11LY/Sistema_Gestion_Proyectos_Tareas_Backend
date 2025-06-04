package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Controller;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.User.DtoUserRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.User.DtoUserResponse;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.User;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iUserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wily/api/users")
public class UserController {

    private final iUserServices userServices;

    public UserController(iUserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping
    public ResponseEntity<List<DtoUserResponse>> getAll() {
        List<DtoUserResponse> users = userServices.getAllUsers()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoUserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(userServices.getUserById(id)));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid DtoUserRequest dto) {
        userServices.createUser(toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid DtoUserRequest dto) {
        User user = toEntity(dto);
        user.setUserId(id);
        userServices.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userServices.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ----- Métodos auxiliares de mapeo (pueden ir en un mapper separado) -----

    private DtoUserResponse toResponse(User user) {
        DtoUserResponse dto = new DtoUserResponse();
        dto.setUserId(user.getUserId());
        dto.setNames(user.getNames());
        dto.setLastnames(user.getLastnames());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private User toEntity(DtoUserRequest dto) {
        User user = new User();
        user.setNames(dto.getNames());
        user.setLastnames(dto.getLastnames());
        user.setCard(dto.getCard());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        return user;
    }

}
