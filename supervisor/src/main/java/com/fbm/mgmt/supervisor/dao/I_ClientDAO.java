package com.fbm.mgmt.supervisor.dao;

import java.util.List;

import com.fbm.mgmt.supervisor.dataobjects.Client;

public interface I_ClientDAO {

	List<Client> getAllClients();

	Client getClientById(int clientId);

	void addClient(Client client);

	void updateClient(Client client);

	void deleteClient(int clientId);
	
	void deleteClient(Client client);

	boolean clientExists(String clientName);
}
