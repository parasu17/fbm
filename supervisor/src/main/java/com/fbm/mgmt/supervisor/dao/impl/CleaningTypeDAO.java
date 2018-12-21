package com.fbm.mgmt.supervisor.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fbm.mgmt.supervisor.dao.I_CleaningTypeDAO;
import com.fbm.mgmt.supervisor.dataobjects.CleaningType;
import com.fbm.mgmt.supervisor.util.FbmUtil;

@Transactional
@Repository
public class CleaningTypeDAO implements I_CleaningTypeDAO {

	private static final String CLEANING_TYPE_EXISTS      = "SELECT count(id) FROM cleaning_type WHERE name = ?";
	private static final String DELETE_CLEANING_TYPE      = "DELETE FROM cleaning_type WHERE id=?";
	private static final String UPDATE_CLEANING_TYPE      = "UPDATE cleaning_type SET description=? WHERE id=?";
	private static final String SELECT_CLEANING_TYPE      = "SELECT * FROM cleaning_type where id = ?";
	private static final String SELECT_ALL_CLEANING_TYPES = "SELECT * FROM cleaning_type";
	
	

	/*

		create table cleaning_type {
			id SERIAL,
			name varchar(255),
			description varchar(255)
			PRIMARY KEY (id	)
		};

	 */


	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<CleaningType> getAllCleaningTypes() {
		List<CleaningType> cleaningTypes = jdbcTemplate.query(SELECT_ALL_CLEANING_TYPES, 
				(rs, num) -> FbmUtil.getObjectFromResultSet(rs, CleaningType.class) );
		return cleaningTypes;
	}

	@Override
	public CleaningType getCleaningTypeById(int cleaningTypeId) {
		CleaningType cleaningType = jdbcTemplate.queryForObject(SELECT_CLEANING_TYPE, 
				(rs, num) -> FbmUtil.getObjectFromResultSet(rs, CleaningType.class), 
				cleaningTypeId);
		return cleaningType;
	}

	@Override
	public void addCleaningType(CleaningType client) {
		CleaningTypePsCreator ps = new CleaningTypePsCreator(client);

		//Add client
		jdbcTemplate.update(ps, ps.getKH());
		
		//Set article id 
		client.setId(ps.getKey());	
	}

	@Override
	public void updateCleaningType(CleaningType client) {
		jdbcTemplate.update(UPDATE_CLEANING_TYPE, client.getDescription(), client.getId());
	}

	@Override
	public void deleteCleaningType(int cleaningTypeId) {
		jdbcTemplate.update(DELETE_CLEANING_TYPE, cleaningTypeId);	}

	@Override
	public void deleteCleaningType(CleaningType client) {
		deleteCleaningType(client.getId());	}

	@Override
	public boolean cleaningTypeExists(String cleaningTypeName) {
		int count = jdbcTemplate.queryForObject(CLEANING_TYPE_EXISTS, Integer.class, cleaningTypeName);
		return count > 0;
	}

}
