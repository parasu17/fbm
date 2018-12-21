package com.fbm.mgmt.supervisor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbm.mgmt.supervisor.dao.I_ClientDAO;
import com.fbm.mgmt.supervisor.dataobjects.Client;
import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.service.I_ClientService;

@Service
public class ClientService implements I_ClientService   {

	@Autowired
	private I_ClientDAO clientDao;

	@Override
	public FbmResponse<List<Client>> getAllClients() {
		return clientDao.getAllClients();
	}

	@Override
	public FbmResponse<List<Client>> getAllClientsFromClientType(int clientTypeId) {
		return clientDao.getAllClientsFromClientType(clientTypeId);
	}

	@Override
	public FbmResponse<Client> getClientById(int clientId) {
		return clientDao.getClientById(clientId);
	}

	@Override
	public FbmResponse<Client> addClient(Client client) {
		if(clientExists(client.getClient_name()).isSuccess()) {
			FbmResponse<Client> res = new FbmResponse<Client>(false, "Client with name '" + client.getClient_name() + "' already exists");
			return res;
		}

		FbmResponse<Client> res = clientDao.addClient(client);

		return res;
	}

	@Override
	public FbmResponse<Client> updateClient(Client client) {
		FbmResponse<Client> res = clientDao.updateClient(client);
		return res;
	}

	@Override
	public FbmResponse<Boolean> deleteClient(int clientId) {
		FbmResponse<Boolean> res = clientDao.deleteClient(clientId);
		return res;
	}

	@Override
	public FbmResponse<Boolean> deleteClient(Client client) {
		FbmResponse<Boolean> res = clientDao.deleteClient(client);
		return res;
	}

	@Override
	public FbmResponse<Boolean> clientExists(String clientName) {
		FbmResponse<Boolean> res = clientDao.clientExists(clientName);
		return res;
	}

}
