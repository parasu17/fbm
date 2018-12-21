package com.fbm.mgmt.supervisor.dataobjects;

import java.util.List;

public class CleaningType {

	private Integer id;
	private String name;
	private String description;
	private List<CleaningSpot> cleaningSpots;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<CleaningSpot> getCleaningSpots() {
		return cleaningSpots;
	}
	public void setCleaningSpots(List<CleaningSpot> cleaningSpots) {
		this.cleaningSpots = cleaningSpots;
	}
	
}
