package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;

public interface iClientServices {

    Client getClientById();
    void createClient(Client client);
    void updateClient(Client client);
    void deleteClient();
    Client getClientByEmail(String email);
    void updateClientPassword(String passwordOld, String passwordNew);

}
