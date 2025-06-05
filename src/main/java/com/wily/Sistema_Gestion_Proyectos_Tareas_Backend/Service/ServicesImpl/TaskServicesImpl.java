package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.ServicesImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Task;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iProjectRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iTaskRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iTaskServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServicesImpl implements iTaskServices {

//    inyeccion por constructor de repositorio task y project
    private final iTaskRepository taskRepository;
    private final iProjectRepository projectRepository;

    public TaskServicesImpl(iTaskRepository taskRepository, iProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

//    creacion de task y enlace con project y verificacion de existencia de project
    @Override
    public Task create(Task task) {
        Project project = projectRepository.findById(task.getProject().getProjectId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        task.setProject(project);
        return taskRepository.save(task);
    }

//    update de task destinado al update unicamente del state de la task
    @Override
    public void updateState(Long taskId, Boolean newState) {
        taskRepository.updateState(taskId, newState);
    }

//    update de la task por id no update de state de task
    @Override
    public void update(Task task, Long taskId) {
        taskRepository.update(task, taskId);
    }

//    delete de task por id
    @Override
    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }

//    obtencion de lista de task por id de project
    @Override
    public List<Task> findByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

}
