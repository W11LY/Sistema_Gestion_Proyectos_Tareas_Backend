package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Controller;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Client.DtoClientRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Client.DtoClientResponse;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Client.DtoClientUpdateRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Dtos.Client.DtoPasswordUpdateRequest;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iClientServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wily/api/client")
public class ClientController {

//    inyeccion por constructor de la capa de servicios por interface
    private final iClientServices clientServices;

    public ClientController(iClientServices clientServices) {
        this.clientServices = clientServices;
    }

//    busqueda por id retorna objeto de client
    @GetMapping
    public ResponseEntity<DtoClientResponse> getById() {
        return ResponseEntity.ok(toResponse(clientServices.getClientById()));
    }

//    creacion de client retorna estatus CREATE no retorna objeto
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid DtoClientRequest dtoClientRequest) {
        clientServices.createClient(toEntity(dtoClientRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    update de client maneja otro dto por la password no retorna objeto retorna status nocontent
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid DtoClientUpdateRequest dtoClientUpdateRequest) {
        Client client = toEntityUpdate(dtoClientUpdateRequest);
        clientServices.updateClient(client);
        return ResponseEntity.noContent().build();
    }

//    delete de client no retorna object retorna status no content
    @DeleteMapping
    public ResponseEntity<Void> delete() {
        clientServices.deleteClient();
        return ResponseEntity.noContent().build();
    }

//    busqueda por email y verificacion de existencia de client
    @GetMapping("/{email}")
    public ResponseEntity<DtoClientResponse> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(toResponse(clientServices.getClientByEmail(email)));
    }

//    update de password resive password actual y nueva retorna un status no content
    @PutMapping("/password")
    public ResponseEntity<Void> update(@RequestBody @Valid DtoPasswordUpdateRequest dtoClientUpdateRequest) {
        clientServices.updateClientPassword(dtoClientUpdateRequest.getPasswordOld(), dtoClientUpdateRequest.getPasswordNew());
        return ResponseEntity.noContent().build();
    }

//    mappeo de entity a dto para response
    private DtoClientResponse toResponse(Client client) {
        DtoClientResponse dtoClientResponse = new DtoClientResponse();
        dtoClientResponse.setUserId(client.getClientId());
        dtoClientResponse.setNames(client.getNames());
        dtoClientResponse.setLastnames(client.getLastnames());
        dtoClientResponse.setEmail(client.getEmail());
        dtoClientResponse.setPhone(client.getPhone());
        return dtoClientResponse;
    }

//    mappeo de dto a entity para request
    private Client toEntity(DtoClientRequest dtoClientRequest) {
        Client client = new Client();
        client.setNames(dtoClientRequest.getNames());
        client.setLastnames(dtoClientRequest.getLastnames());
        client.setPhone(dtoClientRequest.getPhone());
        client.setEmail(dtoClientRequest.getEmail());
        client.setPassword(dtoClientRequest.getPassword());
        return client;
    }

//    mappeo de dto a entity usado unicamente por update
    private Client toEntityUpdate(DtoClientUpdateRequest dtoClientUpdateRequest) {
        Client client = new Client();
        client.setNames(dtoClientUpdateRequest.getNames());
        client.setLastnames(dtoClientUpdateRequest.getLastnames());
        client.setPhone(dtoClientUpdateRequest.getPhone());
        client.setEmail(dtoClientUpdateRequest.getEmail());
        client.setPassword(dtoClientUpdateRequest.getPassword());
        return client;
    }

}
