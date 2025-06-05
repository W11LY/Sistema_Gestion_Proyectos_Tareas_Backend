package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Task;

import java.util.List;
import java.util.Optional;

public interface iProjectRepository {

    Optional<Project> findById(Long id);
    Project save(Project project);
    void update(Project project);
    void deleteById(Long id);
    List<Project> findByClientId(Long clientId);

}
