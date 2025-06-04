package com.wily.task_manager.Jwt.Dtos;


public class DtoAuthResponse {

    private String token;

    public DtoAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
