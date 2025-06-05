package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long clientId;
    private String names;
    private String lastnames;
    private String phone;
    private String email;
    private String password;
    private List<Project> projects;
}
