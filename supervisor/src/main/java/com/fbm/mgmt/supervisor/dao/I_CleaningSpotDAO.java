package com.fbm.mgmt.supervisor.dao;

import java.util.List;

import com.fbm.mgmt.supervisor.dataobjects.CleaningSpot;

public interface I_CleaningSpotDAO {

	public List<CleaningSpot> getAllCleaningSpots(int cleaningTypeId);

}
