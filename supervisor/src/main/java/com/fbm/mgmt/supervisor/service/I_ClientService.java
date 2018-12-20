package com.fbm.mgmt.supervisor.service;

import java.util.List;

import com.fbm.mgmt.supervisor.dataobjects.Client;

public interface I_ClientService {

	List<Client> getAllClients();

	Client getClientById(int clientId);

	boolean addClient(Client client);

	void updateClient(Client client);

	void deleteClient(int clientId);
	
	void deleteClient(Client client);

	boolean clientExists(String clientName);

}
