package com.fbm.mgmt.supervisor.service;

import java.util.List;

import com.fbm.mgmt.supervisor.dataobjects.Client;
import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;

public interface I_ClientService {

	FbmResponse<List<Client>> getAllClients();
	
	FbmResponse<List<Client>> getAllClientsFromClientType(int clientTypeId);

	FbmResponse<Client> getClientById(int clientId);

	FbmResponse<Client> addClient(Client client);

	FbmResponse<Client> updateClient(Client client);

	FbmResponse<Boolean> deleteClient(int clientId);
	
	FbmResponse<Boolean> deleteClient(Client client);

	FbmResponse<Boolean> clientExists(String clientName);

}
