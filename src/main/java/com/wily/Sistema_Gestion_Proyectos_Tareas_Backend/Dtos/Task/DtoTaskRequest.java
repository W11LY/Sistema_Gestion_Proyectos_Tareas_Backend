package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoTaskRequest {
//    uso de dto por seguridad y manejo de validaciones de campos

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no debe superar los 100 caracteres")
    private String title;

    @Size(max = 300, message = "La descripción no debe superar los 300 caracteres")
    private String description;

    @NotNull(message = "El estado es obligatorio")
    private Boolean state;

    @NotNull(message = "El ID del proyecto es obligatorio")
    private Long projectId;

}
