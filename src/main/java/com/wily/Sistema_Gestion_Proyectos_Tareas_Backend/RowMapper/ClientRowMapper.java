package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User u = new User();
        u.setUserId(rs.getLong("user_id"));
        u.setNames(rs.getString("names"));
        u.setLastnames(rs.getString("lastnames"));
        u.setCard(rs.getString("card"));
        u.setPhone(rs.getString("phone"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setProjects(null);
        return u;
    }
}