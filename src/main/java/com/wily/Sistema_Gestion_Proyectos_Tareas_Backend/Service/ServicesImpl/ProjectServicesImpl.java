package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.ServicesImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Task;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iProjectRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iTaskRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iProjectServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServicesImpl  implements iProjectServices {

//    inyeccion por controlador de repositorio, de project y task, current client obtener cliente actual
    private final iProjectRepository projectRepository;
    private final iTaskRepository taskRepository;
    private final CurrentClientService currentClientService;

    public ProjectServicesImpl(iProjectRepository projectRepository, iTaskRepository taskRepository, CurrentClientService currentClientService) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.currentClientService = currentClientService;
    }

//    busqueda por id de project y control de existencia
    @Override
    public Project getProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        List<Task> tasks = taskRepository.findByProjectId(id);
        project.setTasks(tasks);
        return project;
    }

//    creacion de proyecto y enlasar con client por curren client
    @Override
    public Project create(Project project) {
        Client client = currentClientService.getCurrentClient();
        project.setClient(client);
        return projectRepository.save(project);
    }

//    update de project y verificacion de existencia
    @Override
    public void update(Project project) {
        if (!projectRepository.findById(project.getProjectId()).isPresent()) {
            throw new RuntimeException("Proyecto no existe");
        }
        project.setClient(currentClientService.getCurrentClient());
        projectRepository.update(project);
    }

//    eliminacion por id de project
    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

//    obtencion de lista de projectos por id del client obtenido por current client
    @Override
    public List<Project> findByClientId() {
        Client client = currentClientService.getCurrentClient();
        return projectRepository.findByClientId(client.getClientId());
    }
}
