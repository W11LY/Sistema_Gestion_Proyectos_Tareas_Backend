package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoClientResponse {
//    dto destinado a response no maneja validaciones

    private Long userId;
    private String names;
    private String lastnames;
    private String email;
    private String phone;
}
