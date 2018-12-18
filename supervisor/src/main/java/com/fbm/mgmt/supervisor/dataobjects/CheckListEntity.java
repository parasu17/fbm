package com.fbm.mgmt.supervisor.dataobjects;

/**
 * 
 * @author Parasu17
 *
 */
public class CheckListEntity {

	private String clientType;
	private int totalScore;
	private ScoreEntity[] scores;

	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public ScoreEntity[] getScores() {
		return scores;
	}
	public void setScores(ScoreEntity[] scores) {
		this.scores = scores;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
}
