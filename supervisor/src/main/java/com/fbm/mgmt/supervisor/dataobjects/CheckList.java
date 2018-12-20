package com.fbm.mgmt.supervisor.dataobjects;

/**
 * 
 * @author Parasu17
 *
 */
public class CheckList {

	private String clientType;
	private int totalScore;
	private Score[] scores;

	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public Score[] getScores() {
		return scores;
	}
	public void setScores(Score[] scores) {
		this.scores = scores;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
}
