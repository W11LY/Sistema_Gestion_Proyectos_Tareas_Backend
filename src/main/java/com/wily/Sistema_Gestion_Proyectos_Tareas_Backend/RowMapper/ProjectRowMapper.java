package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {
//    usado para transformar el resultado del resultSet del query de jdbc a objecto project
    @Override
    public Project mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Project project = new Project();
        project.setProjectId(resultSet.getLong("project_id"));
        project.setName(resultSet.getString("name"));
        project.setDescription(resultSet.getString("description"));

        Client client = new Client();
        client.setClientId(resultSet.getLong("client_id"));
        project.setClient(client);

        project.setTasks(null);
        return project;
    }
}