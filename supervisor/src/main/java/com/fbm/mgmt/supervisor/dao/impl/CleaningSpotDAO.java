package com.fbm.mgmt.supervisor.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fbm.mgmt.supervisor.dao.I_CleaningSpotDAO;
import com.fbm.mgmt.supervisor.dataobjects.CleaningSpot;
import com.fbm.mgmt.supervisor.util.FbmUtil;

@Transactional
@Repository
public class CleaningSpotDAO implements I_CleaningSpotDAO {

	private static final String SELECT_ALL_CLEANING_SPOTS = "select id, spt_name from cleaning_spots where id in (select cleaning_spot_id from ct_to_cs_map where cleaning_type_id = ? )  order by id";

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<CleaningSpot> getAllCleaningSpots(int cleaningTypeId) {
		List<CleaningSpot> cleaningTypes = jdbcTemplate.query(SELECT_ALL_CLEANING_SPOTS, new Object[] { cleaningTypeId },
				(rs, num) -> FbmUtil.getObjectFromResultSet(rs, CleaningSpot.class) );
		if(cleaningTypes != null) {
			boolean heading = false;
			for(CleaningSpot cs : cleaningTypes) {
				if(cs.getSpt_name() != null) {
					try {
						int doubleDashIndex = cs.getSpt_name().indexOf("--");
						String beforeDash = cs.getSpt_name().substring(0, doubleDashIndex);
						heading = beforeDash.endsWith("h");
						Integer.parseInt(beforeDash.substring(0, heading ? beforeDash.length() - 1 : beforeDash.length()));
						cs.setHeading(heading);
						cs.setSpt_name(cs.getSpt_name().substring(doubleDashIndex + 2));
					} catch(Exception exp) {
						
					}
				}
			}
		}
		return cleaningTypes;
	}

}
