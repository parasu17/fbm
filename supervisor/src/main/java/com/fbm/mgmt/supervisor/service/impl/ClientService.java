package com.fbm.mgmt.supervisor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbm.mgmt.supervisor.dao.I_ClientDAO;
import com.fbm.mgmt.supervisor.dataobjects.Client;
import com.fbm.mgmt.supervisor.service.I_ClientService;

@Service
public class ClientService implements I_ClientService   {

	@Autowired
	private I_ClientDAO clientDao;

	@Override
	public List<Client> getAllClients() {
		return clientDao.getAllClients();
	}

	@Override
	public Client getClientById(int clientId) {
		return clientDao.getClientById(clientId);
	}

	@Override
	public boolean addClient(Client client) {
		if(clientExists(client.getClient_name())) {
			return false;
		}

		clientDao.addClient(client);

		return true;
	}

	@Override
	public void updateClient(Client client) {
		clientDao.updateClient(client);
	}

	@Override
	public void deleteClient(int clientId) {
		clientDao.deleteClient(clientId);
	}

	@Override
	public void deleteClient(Client client) {
		clientDao.deleteClient(client);
	}

	@Override
	public boolean clientExists(String clientName) {
		return clientDao.clientExists(clientName);
	}

}
