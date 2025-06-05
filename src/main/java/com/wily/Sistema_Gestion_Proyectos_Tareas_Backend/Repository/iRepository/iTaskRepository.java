package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Task;

import java.util.List;

public interface iTaskRepository {

    Task save(Task task);
    void update(Task task, Long taskId);
    void updateState(Long id, Boolean newState);
    void deleteById(Long id);
    List<Task> findByProjectId(Long projectId);

}
