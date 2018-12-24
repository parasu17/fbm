package com.fbm.mgmt.supervisor.dataobjects;

/**
 * 
 * @author Parasu17
 *
 */
public class InspectionReport {

	private Integer id;
	private Integer client_id;
	private String client_name;
	private Integer supervisor_id;
	private String supervisor_name;
	private Long date;
	private String feedback;
	private Integer totalscore;
	private Integer score_percent;
	private Score[] scores;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClient_id() {
		return client_id;
	}
	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}
	public Integer getSupervisor_id() {
		return supervisor_id;
	}
	public void setSupervisor_id(Integer supervisor_id) {
		this.supervisor_id = supervisor_id;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public Integer getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(Integer totalScore) {
		this.totalscore = totalScore;
	}
	public Score[] getScores() {
		return scores;
	}
	public void setScores(Score[] scores) {
		this.scores = scores;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public Integer getScore_percent() {
		return score_percent;
	}
	public void setScore_percent(Integer score_percent) {
		this.score_percent = score_percent;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getSupervisor_name() {
		return supervisor_name;
	}
	public void setSupervisor_name(String supervisor_name) {
		this.supervisor_name = supervisor_name;
	}

	

}
