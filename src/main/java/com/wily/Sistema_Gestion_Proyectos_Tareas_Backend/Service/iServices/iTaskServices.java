package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Task;

import java.util.List;

public interface iTaskServices {

    Task getTaskById(Long id);
    Task create(Task task);
    void update(Task task, Long taskId);
    void updateState(Long taskId, Boolean newState);
    void delete(Long taskId);
    List<Task> findByProjectId(Long projectId);
}
