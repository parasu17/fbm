package com.fbm.mgmt.supervisor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbm.mgmt.supervisor.dao.I_CleaningTypeDAO;
import com.fbm.mgmt.supervisor.dataobjects.CleaningType;
import com.fbm.mgmt.supervisor.service.I_CleaningTypeService;

@Service
public class CleaningTypeService implements I_CleaningTypeService {

	@Autowired
	private I_CleaningTypeDAO cleaningTypeDao;

	@Override
	public List<CleaningType> getAllCleaningTypes() {
		return cleaningTypeDao.getAllCleaningTypes();
	}

	@Override
	public CleaningType getCleaningTypeById(int clientId) {
		return cleaningTypeDao.getCleaningTypeById(clientId);
	}

	@Override
	public void addCleaningType(CleaningType client) {
		cleaningTypeDao.addCleaningType(client);
	}

	@Override
	public void updateCleaningType(CleaningType client) {
		cleaningTypeDao.updateCleaningType(client);
	}

	@Override
	public void deleteCleaningType(int cleaningTypeId) {
		cleaningTypeDao.deleteCleaningType(cleaningTypeId);
	}

	@Override
	public void deleteCleaningType(CleaningType client) {
		cleaningTypeDao.deleteCleaningType(client);
	}

	@Override
	public boolean cleaningTypeExists(String cleaningTypeName) {
		return cleaningTypeDao.cleaningTypeExists(cleaningTypeName);
	}

}
