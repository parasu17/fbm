package com.fbm.mgmt.supervisor.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fbm.mgmt.supervisor.dao.I_ClientDAO;
import com.fbm.mgmt.supervisor.dataobjects.Client;

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
	
	private static final Logger log = LoggerFactory.getLogger(ClientDAO.class);

	private static final String CLIENT_EXISTS = "SELECT count(id) FROM clients WHERE client_name = ?";
	private static final String DELETE_CLIENT = "DELETE FROM clients WHERE id=?";
	private static final String UPDATE_CLIENT = "UPDATE clients SET address=?, city=?, province=?, pin=?, country=? WHERE id=?";
	private static final String SELECT_CLIENT = "SELECT * FROM clients where id = ?";
	private static final String SELECT_ALL_CLIENTS = "SELECT * FROM clients";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<Client> getAllClients() {
		RowMapper<Client> rowMapper = new BeanPropertyRowMapper<Client>(Client.class);
		List<Client> clients = jdbcTemplate.query(SELECT_ALL_CLIENTS, rowMapper);
		return clients;
	}

	@Override
	public Client getClientById(int clientId) {
		String sql = SELECT_CLIENT;
		RowMapper<Client> rowMapper = new BeanPropertyRowMapper<Client>(Client.class);
		Client client = jdbcTemplate.queryForObject(sql, rowMapper, clientId);
		return client;
	}

	@Override
	public void addClient(Client client) {
		ClientPsCreator ps = new ClientPsCreator(client);

		//Add client
		int affectedRows = jdbcTemplate.update(ps, ps.getKH());
		
		//Set article id 
		client.setId(ps.getKey());
	}

	@Override
	public void updateClient(Client client) {
		jdbcTemplate.update(UPDATE_CLIENT, client.getAddress(), client.getCity(), client.getProvince(), client.getPin(), client.getCountry(), client.getId());
	}

	@Override
	public void deleteClient(int clientId) {
		jdbcTemplate.update(DELETE_CLIENT, clientId);
	}

	@Override
	public void deleteClient(Client client) {
		deleteClient(client.getId());
	}

	@Override
	public boolean clientExists(String clientName) {
		int count = jdbcTemplate.queryForObject(CLIENT_EXISTS, Integer.class, clientName);
		return count > 0;
	}

}
