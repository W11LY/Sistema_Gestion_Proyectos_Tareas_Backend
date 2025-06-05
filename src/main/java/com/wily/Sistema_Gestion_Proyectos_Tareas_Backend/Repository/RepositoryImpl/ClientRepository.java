package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.RepositoryImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iClientRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.RowMapper.ClientRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClientRepository implements iClientRepository {

//    inyeccion por constructor de jdbc
    private final JdbcTemplate jdbc;
    private final ClientRowMapper rowMapper = new ClientRowMapper();

    public ClientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

//    busqueda por id usada para el profile
    @Override
    public Optional<Client> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM client WHERE client_id = ?", rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

//    creacion de client
    @Override
    public void save(Client client) {
        jdbc.update("""
            INSERT INTO client (names, lastnames, phone, email, password)
            VALUES (?, ?, ?, ?, ?)
        """, client.getNames(), client.getLastnames(), client.getPhone(), client.getEmail(), client.getPassword());
    }

//    update de client sin el atributo password se realiza por separado
    @Override
    public void update(Client client) {
        jdbc.update("""
            UPDATE client SET names = ?, lastnames = ?, phone = ?, email = ?
            WHERE client_id = ?
        """, client.getNames(), client.getLastnames(), client.getPhone(), client.getEmail(), client.getClientId());
    }

//    delete de client por id
    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM client WHERE client_id = ?", id);
    }

//    busqueda de client por email usada principalmente para jwt
    @Override
    public Optional<Client> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject("SELECT * FROM client WHERE email = ?", rowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

//    metodo separado de actualizacion de password
    @Override
    public void updatePassword(String password, Long clientId) {
        jdbc.update("""
            UPDATE client SET password = ?
            WHERE client_id = ?
        """, password, clientId);
    }
}
