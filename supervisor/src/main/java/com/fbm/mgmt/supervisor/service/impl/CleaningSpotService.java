package com.fbm.mgmt.supervisor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbm.mgmt.supervisor.dao.I_CleaningSpotDAO;
import com.fbm.mgmt.supervisor.dataobjects.CleaningSpot;
import com.fbm.mgmt.supervisor.service.I_CleaningSpotService;

@Service
public class CleaningSpotService implements I_CleaningSpotService   {

	@Autowired
	private I_CleaningSpotDAO cleaningSpotDao;

	@Override
	public List<CleaningSpot> getAllCleaningSpots(int cleaningTypeId) {
		return cleaningSpotDao.getAllCleaningSpots(cleaningTypeId);
	}

}
