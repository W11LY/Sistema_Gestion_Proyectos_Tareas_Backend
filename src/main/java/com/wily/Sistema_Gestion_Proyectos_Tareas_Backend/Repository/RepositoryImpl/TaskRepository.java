package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.RepositoryImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Task;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iTaskRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper.TaskRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TaskRepository implements iTaskRepository {

//    inyeccion por constructor de jdbc
    private final JdbcTemplate jdbc;
    private final TaskRowMapper rowMapper = new TaskRowMapper();

    public TaskRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

//    creacion de task y condiguracion para la obtencion del objeto creado
    @Override
    public Task save(Task task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO task (title, description, state, project_id) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setBoolean(3, task.getState());
            preparedStatement.setLong(4, task.getProject().getProjectId());
            return preparedStatement;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            task.setTaskId(key.longValue());
        }

        return task;
    }

//    update separado destinado al update del estado de la task
    @Override
    public void updateState(Long taskId, Boolean newState) {
        jdbc.update("UPDATE task SET state = ? WHERE task_id = ?", newState, taskId);
    }

//    update de task por id
    @Override
    public void update(Task task, Long taskId) {
        jdbc.update("UPDATE task SET title = ?, description = ? WHERE task_id = ?", task.getTitle(), task.getDescription(), taskId);
    }

//    eliminacion de task por id
    @Override
    public void deleteById(Long taskId) {
        jdbc.update("DELETE FROM task WHERE task_id = ?", taskId);
    }

//    obtencion de todas las task enlasadas o pertenecientes a un project por id
    @Override
    public List<Task> findByProjectId(Long projectId) {
        return jdbc.query("SELECT * FROM task WHERE project_id = ?", rowMapper, projectId);
    }
}
