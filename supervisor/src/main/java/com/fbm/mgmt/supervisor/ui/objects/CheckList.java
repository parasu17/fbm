package com.fbm.mgmt.supervisor.ui.objects;

public class CheckList {

	private String checklistName;
	private String heading;
	private String clientType;
	private String clientName;
	private long timeIn;
	
	private Table table;
	
	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public long getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(long timeIn) {
		this.timeIn = timeIn;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getChecklistName() {
		return checklistName;
	}

	public void setChecklistName(String checklistName) {
		this.checklistName = checklistName;
	}

}
