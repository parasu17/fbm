package com.fbm.mgmt.supervisor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.fbm.mgmt.supervisor.dataobjects.Client;

public class ClientPsCreator implements PreparedStatementCreator {

	private static final String ADD_CLIENT = "INSERT INTO clients (client_name, address, city, province, pin, country) values (?, ?, ?, ?, ?, ?)";

	private Client client;
	private KeyHolder keyHolder;
	
	public ClientPsCreator(Client client) {
		this.client = client;
		keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(ADD_CLIENT, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, client.getClient_name());
		ps.setString(2, client.getAddress());
		ps.setString(3, client.getCity());
		ps.setString(4, client.getProvince());
		ps.setString(5, client.getPin());
		ps.setString(6, client.getCountry());
		return ps;
	}

	public KeyHolder getKH() {
		return keyHolder;
	}

	public Integer getKey() {
		Map<String, Object> keys = keyHolder.getKeys();
		if(keys != null) {
			Object obj = keys.get("id");
			if(obj != null && obj instanceof Integer) {
				return (Integer)obj;
			}
		}
		return null;
	}
}
