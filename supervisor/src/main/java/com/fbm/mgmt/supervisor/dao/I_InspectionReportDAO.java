package com.fbm.mgmt.supervisor.dao;

import java.util.List;

import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.InspectionReport;

public interface I_InspectionReportDAO {

	FbmResponse<Boolean> saveInspectionReport(InspectionReport inspectionReport);
	
	FbmResponse<List<InspectionReport>> getAllInspectionReports();

}
