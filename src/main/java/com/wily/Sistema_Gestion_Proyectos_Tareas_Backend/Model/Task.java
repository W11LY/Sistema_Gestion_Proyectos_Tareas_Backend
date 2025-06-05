package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long taskId;
    private String title;
    private String description;
    private Boolean state;
    private Project project;
}
