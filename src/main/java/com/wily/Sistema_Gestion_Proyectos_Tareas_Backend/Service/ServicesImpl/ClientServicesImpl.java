package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.ServicesImpl;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Repository.iRepository.iClientRepository;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iClientServices;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServicesImpl implements iClientServices {

//    inyeccion por constructor de repositorio, password encoder para la contrase;a y currentclient para la obtencion del cliente actual autentificado
    private final iClientRepository userRepository;
    private final CurrentClientService currentClientService;
    private final PasswordEncoder passwordEncoder;

    public ClientServicesImpl(iClientRepository userRepository, CurrentClientService currentClientService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.currentClientService = currentClientService;
        this.passwordEncoder = passwordEncoder;
    }

//    busqueda por id y control de existencia de client
    @Override
    public Client getClientById() {
        return userRepository.findById(currentClientService.getCurrentClient().getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public void createClient(Client client) {
        userRepository.save(client);
    }

//    update con id optenido por current client el cliente autentificado
    @Override
    public void updateClient(Client client) {
        client.setClientId(currentClientService.getCurrentClient().getClientId());
        userRepository.update(client);
    }

//    delete con id optenido por current client el cliente autentificado
    @Override
    public void deleteClient() {
        userRepository.deleteById(currentClientService.getCurrentClient().getClientId());
    }

//    obtencion de client y verificacion de existencia por email
    @Override
    public Client getClientByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

//    update de password y encriptacion usando password encoder asi como validacion de password actual con la almacenada
    @Override
    public void updateClientPassword(String passwordOld, String passwordNew) {
        Client client = currentClientService.getCurrentClient();
        if (!passwordEncoder.matches(passwordOld, client.getPassword())) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }
        userRepository.updatePassword(passwordEncoder.encode(passwordNew), client.getClientId());
    }
}
