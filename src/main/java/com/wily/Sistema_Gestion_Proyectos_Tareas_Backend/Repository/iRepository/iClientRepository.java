package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.User;

import java.util.List;
import java.util.Optional;

public interface iUserRepository {

    List<User> findAll();
    Optional<User> findById(Long id);
    void save(User user);
    void update(User user);
    void deleteById(Long id);

}
