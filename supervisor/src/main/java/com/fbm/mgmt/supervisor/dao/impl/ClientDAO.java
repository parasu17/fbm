package com.fbm.mgmt.supervisor.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fbm.mgmt.supervisor.dao.I_ClientDAO;
import com.fbm.mgmt.supervisor.dataobjects.Client;
import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.util.FbmUtil;

/**
 * 
 * @author Parasu17
 *
 */
@Transactional
@Repository
public class ClientDAO implements I_ClientDAO {

	/*
	 create table clients (
		id SERIAL,
		client_name varchar(255),
		address varchar(255),
		city varchar(255),
		province varchar(255),
		pin varchar(20),
		country varchar(100),
		PRIMARY KEY (id)
	);
	 */
	
	private static final String CLIENT_EXISTS = "SELECT count(id) FROM clients WHERE client_name = ?";
	private static final String DELETE_CLIENT = "DELETE FROM clients WHERE id=?";
	private static final String UPDATE_CLIENT = "UPDATE clients SET address=?, city=?, province=?, pin=?, country=? WHERE id=?";
	private static final String SELECT_CLIENT = "SELECT * FROM clients where id = ?";
	private static final String SELECT_CLIENT_FROM_CLIENT_TYPE = "select * from clients where cleaning_type_id = ?";
	private static final String SELECT_ALL_CLIENTS = "SELECT * FROM clients";
	// query is longitude, latitude
	private static final String SELECT_ALL_CLIENTS_WITHIN = "select * from clients where ST_Within(lat_lng::geometry, ST_Buffer(ST_MakePoint(?, ?)::geography, 1000)::geometry) = true";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public FbmResponse<List<Client>> getAllClients() {
		List<Client> clients = jdbcTemplate.query(SELECT_ALL_CLIENTS, (rs,num) -> FbmUtil.getObjectFromResultSet(rs, Client.class));
		FbmResponse<List<Client>> res = new FbmResponse<List<Client>>(true, null, clients);
		return res;
	}

	@Override
	public FbmResponse<List<Client>> getAllClientsFromClientType(int clientTypeId) {
		List<Client> clients = jdbcTemplate.query(SELECT_CLIENT_FROM_CLIENT_TYPE, (rs,num) -> FbmUtil.getObjectFromResultSet(rs, Client.class) );
		FbmResponse<List<Client>> res = new FbmResponse<List<Client>>(true, null, clients);
		return res;
	}

	@Override
	public FbmResponse<Client> getClientById(int clientId) {
		Client client = jdbcTemplate.queryForObject(SELECT_CLIENT, (rs, num) -> FbmUtil.getObjectFromResultSet(rs, Client.class), clientId);
		FbmResponse<Client> res = new FbmResponse<Client>(true, null, client);
		return res;
	}

	@Override
	public FbmResponse<Client> addClient(Client client) {
		ClientPsCreator ps = new ClientPsCreator(client);

		//Add client
		int affectedRows = jdbcTemplate.update(ps, ps.getKH());
		
		boolean added = affectedRows > 0;
		if(added) {
			//Set article id 
			client.setId(ps.getKey());
		}
		
		FbmResponse<Client> res = new FbmResponse<Client>(added, null, added ? client : null);
		return res;
	}

	@Override
	public FbmResponse<Client> updateClient(Client client) {
		int affectedRows = jdbcTemplate.update(UPDATE_CLIENT, client.getAddress(), client.getCity(), client.getProvince(), client.getPin(), client.getCountry(), client.getId());
		boolean added = affectedRows > 0;
		FbmResponse<Client> res = new FbmResponse<Client>(added, null, added ? client : null);
		return res;
	}

	@Override
	public FbmResponse<Boolean> deleteClient(int clientId) {
		int affectedRows = jdbcTemplate.update(DELETE_CLIENT, clientId);
		boolean deleted = affectedRows > 0;
		FbmResponse<Boolean> res = new FbmResponse<Boolean>(deleted, null, deleted);
		return res;
	}

	@Override
	public FbmResponse<Boolean> deleteClient(Client client) {
		return deleteClient(client.getId());
	}

	@Override
	public FbmResponse<Boolean> clientExists(String clientName) {
		int count = jdbcTemplate.queryForObject(CLIENT_EXISTS, Integer.class, clientName);
		boolean exists = count > 0;
		FbmResponse<Boolean> res = new FbmResponse<Boolean>(exists, null, exists);
		return res;
	}

	@Override
	public FbmResponse<List<Client>> getAllClients(Double lat, Double lng) {
		if(lat == null || lng == null) {
			return getAllClients();
		}

		List<Client> clients = jdbcTemplate.query(SELECT_ALL_CLIENTS_WITHIN, new Object[] {lng, lat}, (rs,num) -> FbmUtil.getObjectFromResultSet(rs, Client.class));
		FbmResponse<List<Client>> res = new FbmResponse<List<Client>>(true, null, clients);
		return res;
	}

}
