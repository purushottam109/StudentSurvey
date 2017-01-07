package com.swe645.assign5;

import java.io.Serializable;

public class SearchSurvey implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String city;
	private String state;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
