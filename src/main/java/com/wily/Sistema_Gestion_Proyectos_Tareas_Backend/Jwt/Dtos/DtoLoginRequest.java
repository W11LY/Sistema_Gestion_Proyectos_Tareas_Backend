package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoLoginRequest {
//    uso de dto por seguridad y manejo de validaciones de campos

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(min = 2, max = 100, message = "El email deben tener entre 2 y 100 caracteres")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$",
            message = "La contraseña debe contener al menos una mayúscula, una minúscula, un número y un carácter especial"
    )
    private String password;
}
