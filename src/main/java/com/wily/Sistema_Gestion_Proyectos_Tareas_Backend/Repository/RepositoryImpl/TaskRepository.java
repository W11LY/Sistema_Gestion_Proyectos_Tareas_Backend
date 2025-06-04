package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Task;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper.TaskRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbc;
    private final TaskRowMapper rowMapper = new TaskRowMapper();

    public TaskRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Task> findAll() {
        return jdbc.query("SELECT * FROM task", rowMapper);
    }

    public void save(Task task) {
        String sql = "INSERT INTO task (title, description, state, project_id) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, task.getTitle(), task.getDescription(), task.getState(), task.getProject().getProjectId());
    }

    public void updateState(Long taskId, Boolean newState) {
        jdbc.update("UPDATE task SET state = ? WHERE task_id = ?", newState, taskId);
    }

    public void deleteById(Long taskId) {
        jdbc.update("DELETE FROM task WHERE task_id = ?", taskId);
    }

    public List<Task> findByProjectId(Long projectId) {
        return jdbc.query("SELECT * FROM task WHERE project_id = ?", rowMapper, projectId);
    }
}
