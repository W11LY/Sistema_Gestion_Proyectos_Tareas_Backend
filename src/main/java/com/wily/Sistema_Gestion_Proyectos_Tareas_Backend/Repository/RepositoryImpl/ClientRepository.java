package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.RepositoryImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.User;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iUserRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper.UserRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements iUserRepository {

    private final JdbcTemplate jdbc;
    private final UserRowMapper rowMapper = new UserRowMapper();

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("SELECT * FROM users", rowMapper);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM users WHERE user_id = ?", rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(User user) {
        jdbc.update("""
            INSERT INTO users (names, lastnames, card, phone, email, password)
            VALUES (?, ?, ?, ?, ?)
        """, user.getNames(), user.getLastnames(), user.getCard(), user.getPhone(), user.getEmail());
    }

    @Override
    public void update(User user) {
        jdbc.update("""
            UPDATE users SET names = ?, lastnames = ?, card = ?, phone = ?, email = ?
            WHERE user_id = ?
        """, user.getNames(), user.getLastnames(), user.getCard(), user.getPhone(), user.getEmail(), user.getUserId());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM users WHERE user_id = ?", id);
    }
}
