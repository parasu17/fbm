package com.fbm.mgmt.supervisor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.fbm.mgmt.supervisor.dataobjects.CleaningType;

public class CleaningTypePsCreator implements PreparedStatementCreator {

	private static final String ADD_CLEANING_TYPE = "INSERT INTO cleaning_type (name, description) values (?, ?)";

	private CleaningType client;
	private KeyHolder keyHolder;
	
	public CleaningTypePsCreator(CleaningType client) {
		this.client = client;
		keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(ADD_CLEANING_TYPE, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, client.getName());
		ps.setString(2, client.getDescription());
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
