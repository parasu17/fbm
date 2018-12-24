package com.fbm.mgmt.supervisor.dataobjects;

/**
 * 
 * @author Parasu17
 *
 */
public class InspectionReport {

	private Integer id;
	private Integer client_id;
	private Integer supervisor_id;
	private Long date;
	private String feedBack;
	private Integer totalScore;
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
	public String getFeedBack() {
		return feedBack;
	}
	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
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

	

}
