package com.fbm.mgmt.supervisor.dataobjects;

/*
 create table clients (
	id SERIAL,
	client_name varchar(255),
	address varchar(255),
	city varchar(255),
	province varchar(255),
	pin varchar(20),
	country varchar(100),
	PRIMARY KEY (id)
);
 */
public class Client {

	private Integer id;
	private String client_name;
	private Integer cleaning_type_id;
	private String address;
	private String city;
	private String province;
	private String pin;
	private String country;
	private Double latitude;
	private Double longitude;
	private CleaningType cleaningType;
	
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getCleaning_type_id() {
		return cleaning_type_id;
	}
	public void setCleaning_type_id(Integer cleaning_type_id) {
		this.cleaning_type_id = cleaning_type_id;
	}
	public CleaningType getCleaningType() {
		return cleaningType;
	}
	public void setCleaningType(CleaningType cleaningType) {
		this.cleaningType = cleaningType;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
