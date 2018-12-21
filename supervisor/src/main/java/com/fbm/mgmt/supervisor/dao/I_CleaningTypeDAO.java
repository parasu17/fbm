package com.fbm.mgmt.supervisor.dao;

import java.util.List;

import com.fbm.mgmt.supervisor.dataobjects.CleaningType;

public interface I_CleaningTypeDAO {

	List<CleaningType> getAllCleaningTypes();

	CleaningType getCleaningTypeById(int clientId);

	void addCleaningType(CleaningType client);

	void updateCleaningType(CleaningType client);

	void deleteCleaningType(int cleaningTypeId);
	
	void deleteCleaningType(CleaningType client);

	boolean cleaningTypeExists(String cleaningTypeName);

}
