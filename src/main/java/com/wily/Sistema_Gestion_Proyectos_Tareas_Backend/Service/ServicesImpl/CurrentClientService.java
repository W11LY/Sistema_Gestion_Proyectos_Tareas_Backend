package com.wily.task_manager.Service;

import com.wily.task_manager.Model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {
            return user;
        } else {
            throw new RuntimeException("No se pudo obtener el usuario autenticado");
        }
    }
}
