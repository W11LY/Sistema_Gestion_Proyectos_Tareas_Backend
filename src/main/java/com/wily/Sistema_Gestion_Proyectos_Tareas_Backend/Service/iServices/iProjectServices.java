package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;

import java.util.List;

public interface iProjectServices {

    Project getProjectById(Long id);
    Project create(Project project);
    void update(Project project);
    void delete(Long id);
    List<Project> findByClientId();

}
