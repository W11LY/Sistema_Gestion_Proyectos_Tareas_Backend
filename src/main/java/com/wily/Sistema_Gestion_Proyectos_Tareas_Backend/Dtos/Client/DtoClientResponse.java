package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUserResponse {

    private Long userId;
    private String names;
    private String lastnames;
    private String email;

}
