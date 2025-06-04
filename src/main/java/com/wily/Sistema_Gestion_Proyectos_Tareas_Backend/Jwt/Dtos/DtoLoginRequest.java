package com.wily.task_manager.Jwt.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoLoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
