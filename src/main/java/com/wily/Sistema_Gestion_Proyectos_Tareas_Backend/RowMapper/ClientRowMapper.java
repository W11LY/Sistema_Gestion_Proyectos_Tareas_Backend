package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
//    usado para transformar el resultado del resultSet del query de jdbc a objecto client
    @Override
    public Client mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Client client = new Client();
        client.setClientId(resultSet.getLong("client_id"));
        client.setNames(resultSet.getString("names"));
        client.setLastnames(resultSet.getString("lastnames"));
        client.setPhone(resultSet.getString("phone"));
        client.setEmail(resultSet.getString("email"));
        client.setPassword(resultSet.getString("password"));
        client.setProjects(null);
        return client;
    }
}