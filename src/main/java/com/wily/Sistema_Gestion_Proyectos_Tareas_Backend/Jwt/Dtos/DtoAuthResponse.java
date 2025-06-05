package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Dtos;


public class DtoAuthResponse {

    private String token;

    public DtoAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
