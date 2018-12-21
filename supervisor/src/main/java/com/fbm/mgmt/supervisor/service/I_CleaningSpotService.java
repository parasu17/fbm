package com.fbm.mgmt.supervisor.service;

import java.util.List;

import com.fbm.mgmt.supervisor.dataobjects.CleaningSpot;

public interface I_CleaningSpotService {

	public List<CleaningSpot> getAllCleaningSpots(int cleaningTypeId);

}
