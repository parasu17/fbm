package com.fbm.mgmt.supervisor.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.fbm.mgmt.supervisor.dataobjects.InspectionReport;

public class InspectionReportPsCreator implements PreparedStatementCreator {

	private static final String ADD_INSPECTION_REPORT = "INSERT INTO inspection_report (client_id, supervisor_id, date, feedBack, totalScore, score_percent) values (?, ?, ?, ?, ?, ?)";

	private InspectionReport inspectionReport;
	private KeyHolder keyHolder;

	public InspectionReportPsCreator(InspectionReport inspectionReport) {
		this.inspectionReport = inspectionReport;
		keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(ADD_INSPECTION_REPORT, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, inspectionReport.getClient_id());
		ps.setInt(2, inspectionReport.getSupervisor_id());
		ps.setLong(3, inspectionReport.getDate());
		ps.setString(4, inspectionReport.getFeedback());
		ps.setInt(5, inspectionReport.getTotalscore());
		ps.setInt(6, inspectionReport.getScore_percent());
		return ps;
	}

	public KeyHolder getKH() {
		return keyHolder;
	}

	public Integer getKey() {
		Map<String, Object> keys = keyHolder.getKeys();
		if(keys != null) {
			Object obj = keys.get("id");
			if(obj != null && obj instanceof Integer) {
				return (Integer)obj;
			}
		}
		return null;
	}
}
