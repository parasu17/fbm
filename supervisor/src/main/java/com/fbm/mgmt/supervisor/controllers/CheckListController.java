package com.fbm.mgmt.supervisor.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.InspectionReport;
import com.fbm.mgmt.supervisor.service.I_InspectionReportService;

@RestController
@RequestMapping("/services/inspection/")
public class CheckListController {

	private final Logger log = LoggerFactory.getLogger(CheckListController.class);
	
	@Autowired
	private I_InspectionReportService inspectionReportService;

	@PostMapping("/saveInspectionReport")
	@ResponseBody
	public FbmResponse<Boolean> saveInspectionReport(@RequestBody InspectionReport inspectionReport) {
		FbmResponse<Boolean> res = inspectionReportService.saveInspectionReport(inspectionReport);
		return res;
	}

	@RequestMapping("/getAllInspectionReports")
	@ResponseBody
	public FbmResponse<List<InspectionReport>> getAllInspectionReports() {
		FbmResponse<List<InspectionReport>> res = inspectionReportService.getAllInspectionReports();
		return res;
	}
}
