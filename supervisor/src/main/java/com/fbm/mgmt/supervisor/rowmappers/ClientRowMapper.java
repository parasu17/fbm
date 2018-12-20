package com.fbm.mgmt.supervisor.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.fbm.mgmt.supervisor.dataobjects.Client;
import com.fbm.mgmt.supervisor.util.FbmUtil;

public class ClientRowMapper implements RowMapper<Client> {

	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
		return FbmUtil.getObjectFromResultSet(rs, Client.class);
	}

}
