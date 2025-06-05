package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoTaskResponse {
//    dto destinado a response no maneja validaciones

    private Long taskId;
    private String title;
    private String description;
    private Boolean state;
    private Long projectId;

}
