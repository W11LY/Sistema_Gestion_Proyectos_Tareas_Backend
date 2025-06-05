package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoProjectRequest {
//    uso de dto por seguridad y manejo de validaciones de campos

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String name;

    @Size(max = 300, message = "La descripción no debe superar los 300 caracteres")
    private String description;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long clientId;

}
