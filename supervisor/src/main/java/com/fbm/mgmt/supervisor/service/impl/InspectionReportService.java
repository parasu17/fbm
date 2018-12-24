package com.fbm.mgmt.supervisor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbm.mgmt.supervisor.dao.I_InspectionReportDAO;
import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.InspectionReport;
import com.fbm.mgmt.supervisor.service.I_InspectionReportService;

@Service
public class InspectionReportService implements I_InspectionReportService {

	@Autowired
	private I_InspectionReportDAO inspectionReportDao;

	@Override
	public FbmResponse<Boolean> saveInspectionReport(InspectionReport inspectionReport) {
		FbmResponse<Boolean> res = inspectionReportDao.saveInspectionReport(inspectionReport);
		return res;
	}

}
