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
public class Project {

    private Long projectId;
    private String name;
    private String description;
    private Client client;
    private List<Task> tasks;
}
