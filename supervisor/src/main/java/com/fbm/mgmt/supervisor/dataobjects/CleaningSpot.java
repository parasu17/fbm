package com.fbm.mgmt.supervisor.dataobjects;

public class CleaningSpot {

	private Integer id;
	private String spt_name;
	private boolean heading;

	public CleaningSpot(int id, String sptName) {
		this.id = id;
		this.spt_name = sptName;
	}

	public CleaningSpot() {
		
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSpt_name() {
		return spt_name;
	}
	public void setSpt_name(String spt_name) {
		this.spt_name = spt_name;
	}

	public boolean isHeading() {
		return heading;
	}

	public void setHeading(boolean heading) {
		this.heading = heading;
	}
}
