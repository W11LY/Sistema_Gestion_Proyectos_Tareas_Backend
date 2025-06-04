package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper.ProjectRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbc;
    private final ProjectRowMapper rowMapper = new ProjectRowMapper();

    public ProjectRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Project> findAll() {
        return jdbc.query("SELECT * FROM project", rowMapper);
    }

    public Optional<Project> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM project WHERE project_id = ?", rowMapper, id)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(Project p) {
        jdbc.update("INSERT INTO project (name, description, user_id) VALUES (?, ?, ?)",
                p.getName(), p.getDescription(), p.getUser().getUserId());
    }

    public void update(Project p) {
        jdbc.update("UPDATE project SET name = ?, description = ?, user_id = ? WHERE project_id = ?",
                p.getName(), p.getDescription(), p.getUser().getUserId(), p.getProjectId());
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM project WHERE project_id = ?", id);
    }
}
