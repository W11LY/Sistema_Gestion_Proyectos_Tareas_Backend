package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {
    //    usado para transformar el resultado del resultSet del query de jdbc a objecto task
    @Override
    public Task mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Task task = new Task();
        task.setTaskId(resultSet.getLong("task_id"));
        task.setTitle(resultSet.getString("title"));
        task.setDescription(resultSet.getString("description"));
        task.setState(resultSet.getBoolean("state"));

        Project project = new Project();
        project.setProjectId(resultSet.getLong("project_id"));
        task.setProject(project);

        return task;
    }
}