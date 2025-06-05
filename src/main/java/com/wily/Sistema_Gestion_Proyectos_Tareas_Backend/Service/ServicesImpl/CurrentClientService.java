package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.ServicesImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentClientService {

//    usado para la obtencion del cliente autentificdo actualmente
    public Client getCurrentClient() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Client client) {
            return client;
        } else {
            throw new RuntimeException("No se pudo obtener el cliente autenticado");
        }
    }
}
