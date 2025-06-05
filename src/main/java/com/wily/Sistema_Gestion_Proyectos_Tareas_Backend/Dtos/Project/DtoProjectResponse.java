package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoProjectResponse {
//    dto destinado a response no maneja validaciones

    private Long projectId;
    private String name;
    private String description;
    private Long clientId;

}
