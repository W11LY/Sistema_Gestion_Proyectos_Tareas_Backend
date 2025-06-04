package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.ServicesImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.User;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iUserRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iUserServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements iUserServices {

    private final iUserRepository userRepository;

    public UserServicesImpl(iUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        if (!userRepository.findById(user.getUserId()).isPresent()) {
            throw new RuntimeException("Usuario no existe");
        }
        userRepository.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
