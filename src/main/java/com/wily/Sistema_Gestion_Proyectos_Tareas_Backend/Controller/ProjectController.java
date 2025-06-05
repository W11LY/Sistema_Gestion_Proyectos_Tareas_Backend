package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Controller;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Project.DtoProjectRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Project.DtoProjectResponse;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iProjectServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/wily/api/projects")
public class ProjectController {

    //    inyeccion por constructor de la capa de servicios por interface
    private final iProjectServices projectServices;

    public ProjectController(iProjectServices projectServices) {
        this.projectServices = projectServices;
    }

//    busqueda por id de project
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(toResponse(projectServices.getProjectById(id)));
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
        }
    }

//    creacion de project y enlase a client respectivo retorna object project
    @PostMapping
    public ResponseEntity<DtoProjectResponse> create(@RequestBody @Valid DtoProjectRequest dtoProjectRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(projectServices.create(toEntity(dtoProjectRequest))));
    }

//    update de project id se resive por url y se actualiza en el objeto
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid DtoProjectRequest dtoProjectRequest) {
        try{
           Project project = toEntity(dtoProjectRequest);
           project.setProjectId(id);
           projectServices.update(project);
           return ResponseEntity.noContent().build();
       } catch (ResponseStatusException ex) {
           return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
       } catch (Exception ex) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
       }
    }

//    eliminacion del projecto por id retorna status no content
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            projectServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
        }
    }

//    busqueda de todos los projectos enlasados a un cliente id retorna lista de project
    @GetMapping("/projects")
    public ResponseEntity<List<DtoProjectResponse>> getProjectByClient() {
        List<Project> projects = projectServices.findByClientId();
        List<DtoProjectResponse> dtoProjectResponses = projects.stream()
                .map(projec -> {
                    DtoProjectResponse dtoProjectResponse = new DtoProjectResponse();
                    dtoProjectResponse.setName(projec.getName());
                    dtoProjectResponse.setDescription(projec.getDescription());
                    dtoProjectResponse.setClientId(projec.getClient().getClientId());
                    dtoProjectResponse.setProjectId(projec.getProjectId());
                    return dtoProjectResponse;
                }).toList();
        return ResponseEntity.ok(dtoProjectResponses);
    }

//    mappeo de entoty a dto para response
    private DtoProjectResponse toResponse(Project project) {
        DtoProjectResponse dtoProjectResponse = new DtoProjectResponse();
        dtoProjectResponse.setProjectId(project.getProjectId());
        dtoProjectResponse.setName(project.getName());
        dtoProjectResponse.setDescription(project.getDescription());
        dtoProjectResponse.setClientId(project.getClient().getClientId());
        return dtoProjectResponse;
    }

//    mappeo de dto a entity para request
    private Project toEntity(DtoProjectRequest dtoProjectRequest) {
        Project project = new Project();
        project.setName(dtoProjectRequest.getName());
        project.setDescription(dtoProjectRequest.getDescription());

        Client client = new Client();
        client.setClientId(dtoProjectRequest.getClientId());
        project.setClient(client);

        return project;
    }

}
