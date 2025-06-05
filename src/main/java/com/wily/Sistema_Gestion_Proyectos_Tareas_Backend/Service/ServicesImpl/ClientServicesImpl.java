package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.ServicesImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iClientRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iClientServices;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClientServicesImpl implements iClientServices {

//    inyeccion por constructor de repositorio, password encoder para la contrase;a y currentclient para la obtencion del cliente actual autentificado
    private final iClientRepository clientRepository;
    private final CurrentClientService currentClientService;
    private final PasswordEncoder passwordEncoder;

    public ClientServicesImpl(iClientRepository clientRepository, CurrentClientService currentClientService, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.currentClientService = currentClientService;
        this.passwordEncoder = passwordEncoder;
    }

//    busqueda por id y control de existencia de client
    @Override
    public Client getClientById() {
        return clientRepository.findById(currentClientService.getCurrentClient().getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Cliente no encontrado"));
    }

    @Override
    public void createClient(Client client) {
        clientRepository.save(client);
    }

//    update con id optenido por current client el cliente autentificado
    @Override
    public void updateClient(Client client) {
        if (!client.getEmail().equals(currentClientService.getCurrentClient().getEmail())) {
            if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está en uso");
            }
        }

        client.setClientId(currentClientService.getCurrentClient().getClientId());
        clientRepository.update(client);
    }

//    delete con id optenido por current client el cliente autentificado
    @Override
    public void deleteClient() {
        clientRepository.deleteById(currentClientService.getCurrentClient().getClientId());
    }

//    obtencion de client y verificacion de existencia por email
    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Cliente no encontrado"));
    }

//    update de password y encriptacion usando password encoder asi como validacion de password actual con la almacenada
    @Override
    public void updateClientPassword(String passwordOld, String passwordNew) {
        Client client = currentClientService.getCurrentClient();
        if (!passwordEncoder.matches(passwordOld, client.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrectos");
        }
        clientRepository.updatePassword(passwordEncoder.encode(passwordNew), client.getClientId());
    }
}
