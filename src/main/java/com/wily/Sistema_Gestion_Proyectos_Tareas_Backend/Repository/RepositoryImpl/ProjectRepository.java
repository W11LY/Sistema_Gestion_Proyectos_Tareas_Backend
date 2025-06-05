package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.RepositoryImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Project;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iProjectRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper.ProjectRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository implements iProjectRepository {

//    inyeccion por constructor de jdbc
    private final JdbcTemplate jdbc;
    private final ProjectRowMapper rowMapper = new ProjectRowMapper();

    public ProjectRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
//busqueda de projecto por id
    @Override
    public Optional<Project> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM project WHERE project_id = ?", rowMapper, id)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

//    creacion de project con configuracion y obtencion de objeto almacenado
    @Override
    public Project save(Project project) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO project (name, description, client_id) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setLong(3, project.getClient().getClientId());
            return preparedStatement;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            project.setProjectId(key.longValue());
        }

        return project;
    }


//    update de project
    @Override
    public void update(Project p) {
        jdbc.update("UPDATE project SET name = ?, description = ? WHERE project_id = ?",
                p.getName(), p.getDescription(), p.getProjectId());
    }

//    delete por id de project
    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM project WHERE project_id = ?", id);
    }

//    obtencion de todos los proyectos enlasados con el cliente de id
    @Override
    public List<Project> findByClientId(Long clientId) {
        return jdbc.query("SELECT * FROM project WHERE client_id = ?", rowMapper, clientId);
    }
}
