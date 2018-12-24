package com.fbm.mgmt.supervisor.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fbm.mgmt.supervisor.dao.I_InspectionReportDAO;
import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.InspectionReport;
import com.fbm.mgmt.supervisor.dataobjects.Score;
import com.fbm.mgmt.supervisor.util.FbmUtil;

@Transactional
@Repository
public class InspectionReportDAO implements I_InspectionReportDAO {

	private static final String INSPECTION_REPORT_EXISTS = "select id from inspection_report where client_id = ? and supervisor_id = ? and date = ?";
	private static final String DELETE_SCORE = "delete from score where inspection_report_id = ?";
	private static final String DELETE_INSPECTION_REPORT = "delete from inspection_report where id = ?";
	
	private static final String GET_ALL_INSPECTION_REPORTS = "select ir.id, ir.client_id, ir.supervisor_id, ir.date, ir.feedback, ir.totalscore, ir.score_percent, c.client_name, u.name as supervisor_name from inspection_report ir, clients c, users u where ir.client_id = c.id and ir.supervisor_id = u.id";

	@Autowired
    private JdbcTemplate jdbcTemplate;

	private Integer inspectionReportExists(InspectionReport inspectionReport) {
		Integer inspection_report_id = jdbcTemplate.query(
				INSPECTION_REPORT_EXISTS, 
				new Object[] { inspectionReport.getClient_id(), inspectionReport.getSupervisor_id(), inspectionReport.getDate() },
				rs -> { if(!rs.next()) { return null; } return rs.getInt(1); });
		return inspection_report_id;
	}

	private void deleteExistingInspectionReport(InspectionReport inspectionReport) {
		jdbcTemplate.update(DELETE_SCORE, inspectionReport.getId());
		jdbcTemplate.update(DELETE_INSPECTION_REPORT, inspectionReport.getId());
	}

	@Override
	public FbmResponse<Boolean> saveInspectionReport(InspectionReport inspectionReport) {
		Integer inspection_report_id = inspectionReportExists(inspectionReport);
		if(inspection_report_id != null) {
			inspectionReport.setId(inspection_report_id);
			deleteExistingInspectionReport(inspectionReport);
			inspectionReport.setId(null);
		}
		InspectionReportPsCreator ps = new InspectionReportPsCreator(inspectionReport);
		jdbcTemplate.update(ps, ps.getKH());
		inspectionReport.setId(ps.getKey());

		if(inspectionReport.getScores() != null) {
			for(Score score : inspectionReport.getScores()) {
				score.setInspection_report_id(inspectionReport.getId());
			}
			jdbcTemplate.batchUpdate(InspectionReportScoreBatchPsSetter.ADD_SCORE, new InspectionReportScoreBatchPsSetter(inspectionReport.getScores()));
		}
		FbmResponse<Boolean> res = new FbmResponse<Boolean>(true, null, true);
		return res;
	}

	@Override
	public FbmResponse<List<InspectionReport>> getAllInspectionReports() {
		List<InspectionReport> irs = jdbcTemplate.query(GET_ALL_INSPECTION_REPORTS, (rs,num) -> FbmUtil.getObjectFromResultSet(rs, InspectionReport.class));
		FbmResponse<List<InspectionReport>> res = new FbmResponse<List<InspectionReport>>(true, null, irs);
		return res;
	}

	
}
