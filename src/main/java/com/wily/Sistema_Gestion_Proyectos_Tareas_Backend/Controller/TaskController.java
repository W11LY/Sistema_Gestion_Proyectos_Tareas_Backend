package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Controller;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Task.DtoTaskRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Task.DtoTaskResponse;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Task;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iTaskServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wily/api/tasks")
public class TaskController {

    private final iTaskServices taskServices;

    public TaskController(iTaskServices taskServices) {
        this.taskServices = taskServices;
    }

    @PostMapping
    public ResponseEntity<DtoTaskResponse> create(@RequestBody @Valid DtoTaskRequest dtoTaskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(taskServices.create(toEntity(dtoTaskRequest))));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> update(@Valid @RequestBody DtoTaskRequest dtoTaskRequest, @PathVariable Long taskId) {
        taskServices.update(toEntity(dtoTaskRequest), taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}/{state}")
    public ResponseEntity<Void> updateState(@PathVariable Long taskId, @PathVariable Boolean state) {
        taskServices.updateState(taskId, state);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(@PathVariable Long taskId) {
        taskServices.delete(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<DtoTaskResponse>> getTasksByProject(@PathVariable Long id) {
        List<Task> tasks = taskServices.findByProjectId(id);
        List<DtoTaskResponse> dtoTaskResponses = tasks.stream()
                .map(task -> {
                    DtoTaskResponse dto = new DtoTaskResponse();
                    dto.setTaskId(task.getTaskId());
                    dto.setTitle(task.getTitle());
                    dto.setDescription(task.getDescription());
                    dto.setState(task.getState());
                    dto.setProjectId(id);
                    return dto;
                }).toList();
        return ResponseEntity.ok(dtoTaskResponses);
    }


    private DtoTaskResponse toResponse(Task task) {
        DtoTaskResponse dtoTaskResponse = new DtoTaskResponse();
        dtoTaskResponse.setTaskId(task.getTaskId());
        dtoTaskResponse.setTitle(task.getTitle());
        dtoTaskResponse.setDescription(task.getDescription());
        dtoTaskResponse.setState(task.getState());
        dtoTaskResponse.setProjectId(task.getProject().getProjectId());
        return dtoTaskResponse;
    }

    private Task toEntity(DtoTaskRequest dtoTaskRequest) {
        Task task = new Task();
        task.setTitle(dtoTaskRequest.getTitle());
        task.setDescription(dtoTaskRequest.getDescription());
        task.setState(dtoTaskRequest.getState());

        Project project = new Project();
        project.setProjectId(dtoTaskRequest.getProjectId());
        task.setProject(project);

        return task;
    }

}
