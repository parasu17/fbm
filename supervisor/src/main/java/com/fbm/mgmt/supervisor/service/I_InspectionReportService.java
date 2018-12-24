package com.fbm.mgmt.supervisor.service;

import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.InspectionReport;

public interface I_InspectionReportService {

	FbmResponse<Boolean> saveInspectionReport(InspectionReport inspectionReport);

}
