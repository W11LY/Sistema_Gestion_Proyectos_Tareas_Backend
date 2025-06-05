package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;

import java.util.Optional;

public interface iClientRepository {

    Optional<Client> findById(Long id);
    void save(Client client);
    void update(Client client);
    void deleteById(Long id);
    Optional<Client> findByEmail(String email);
    void updatePassword(String password, Long clientId);

}
