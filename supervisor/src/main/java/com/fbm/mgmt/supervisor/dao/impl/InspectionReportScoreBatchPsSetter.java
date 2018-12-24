package com.fbm.mgmt.supervisor.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.fbm.mgmt.supervisor.dataobjects.Score;

public class InspectionReportScoreBatchPsSetter implements BatchPreparedStatementSetter {

	public static final String ADD_SCORE = "INSERT INTO score (inspection_report_id, cleaning_spot_name, score, comments) values (?, ?, ?, ?)";

	private Score[] scores;

	public InspectionReportScoreBatchPsSetter(Score[] scores) {
		this.scores = scores;
	}

	@Override
	public void setValues(PreparedStatement ps, int i) throws SQLException {
		ps.setInt(1, scores[i].getInspection_report_id());
		ps.setString(2, scores[i].getCleaning_spot_name());
		ps.setInt(3, scores[i].getScore());
		ps.setString(4, scores[i].getComments());
	}

	@Override
	public int getBatchSize() {
		return scores.length;
	}

}
