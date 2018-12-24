package com.fbm.mgmt.supervisor.dataobjects;

/**
 * 
 * @author Parasu17
 *
 */
public class Score {

	private Integer id;
	private Integer inspection_report_id;
	private String cleaning_spot_name;
	private Integer score;
	private String comments;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInspection_report_id() {
		return inspection_report_id;
	}
	public void setInspection_report_id(Integer inspection_report_id) {
		this.inspection_report_id = inspection_report_id;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCleaning_spot_name() {
		return cleaning_spot_name;
	}
	public void setCleaning_spot_name(String cleaning_spot_name) {
		this.cleaning_spot_name = cleaning_spot_name;
	}

}
