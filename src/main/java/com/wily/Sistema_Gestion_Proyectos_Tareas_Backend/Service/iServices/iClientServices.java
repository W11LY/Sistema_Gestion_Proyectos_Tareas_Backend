package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.User;

import java.util.List;

public interface iUserServices {

    List<User> getAllUsers();
    User getUserById(Long id);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);

}
