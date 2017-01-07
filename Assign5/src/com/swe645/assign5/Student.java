package com.swe645.assign5;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;



public class Student
{
	private static final String TAB_SEPERATOR = "\t";
	private static final String COMMA_SEPERATOR = ",";
	private boolean isCityListDisabled = true;

	private long id;
	private String firstName;
	private String lastName;
	private String streetAddress;
	private String city;
	private String state;
	private String zipCode;
	private String phoneNumber;
	private String email;
	private Date surveyDate;
	private List<String> campusLikes;
	private String howInterested;
	private String likelihood;
	private String raffle;
	private String comments;
	private Date semStartDate;
	private String ecName;
	private String ecTelNum;
	private String ecEmail;
	private long ecid;

	public long getEcid() {
		return ecid;
	}

	public void setEcid(long ecid) {
		this.ecid = ecid;
	}

	public Student() {
		System.out.println("Student started..!!");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.trim();
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress.trim();
	}

	public String getCity()
	{
		System.out.println("Get City: " + city);
		return city;
	}

	public void setCity(String city) {
		System.out.println("Set City: " + city);
		this.city = city.trim();
	}

	public String getState() {
		System.out.println(state);
		return state;
	}

	public void setState(String state) {
		System.out.println(state);
		this.state = state.trim();
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode.trim();
		this.city = "";
		isCityListDisabled = false;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public Date getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	public List<String> getCampusLikes() {
		return campusLikes;
	}

	public void setCampusLikes(List<String> campusLikes) {
		this.campusLikes = campusLikes;
	}

	public String getHowInterested() {
		return howInterested;
	}

	public void setHowInterested(String howInterested) {
		this.howInterested = howInterested.trim();
	}

	public String getLikelihood() {
		return likelihood;
	}

	public void setLikelihood(String likelihood) {
		this.likelihood = likelihood.trim();
	}

	public String getRaffle() {
		return raffle;
	}

	public void setRaffle(String raffle) {
		this.raffle = raffle.trim();
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments.trim();
	}

	public Date getSemStartDate() {
		return semStartDate;
	}

	public void setSemStartDate(Date semStartDate) {
		this.semStartDate = semStartDate;
	}

	public void showData()
	{
		System.out.println("Data Saved");
	}

	public boolean isCityListDisabled()
	{
		return(isCityListDisabled);
	}

	public List<String> getZipCodes()
	{
		return(ZipInfo.ZIP_CODES);
	}

	public Map<String,String> getCities()
	{
		return(ZipInfo.ZIP_MAP.get(zipCode));
	}

	public String getEcName() {
		return ecName;
	}

	public void setEcName(String ecName) {
		this.ecName = ecName;
	}

	public String getEcTelNum() {
		return ecTelNum;
	}

	public void setEcTelNum(String ecTelNum) {
		this.ecTelNum = ecTelNum;
	}

	public String getEcEmail() {
		return ecEmail;
	}

	public void setEcEmail(String ecEmail) {
		this.ecEmail = ecEmail;
	}

	@Override
	public String toString()
	{
		StringBuilder sbData = new StringBuilder();

		sbData.append("FirstName:");
		sbData.append(firstName);
		sbData.append(TAB_SEPERATOR);

		sbData.append("LastName:");
		sbData.append(lastName);
		sbData.append(TAB_SEPERATOR);

		sbData.append("StreetAddress:");
		sbData.append(streetAddress);
		sbData.append(TAB_SEPERATOR);

		sbData.append("City:");
		sbData.append(city);
		sbData.append(TAB_SEPERATOR);

		sbData.append("State:");
		sbData.append(state);
		sbData.append(TAB_SEPERATOR);

		sbData.append("ZipCode:");
		sbData.append(zipCode);
		sbData.append(TAB_SEPERATOR);

		sbData.append("TelephoneNumber:");
		sbData.append(phoneNumber);
		sbData.append(TAB_SEPERATOR);

		sbData.append("Email:");
		sbData.append(email);
		sbData.append(TAB_SEPERATOR);

		sbData.append("DateOfSurvey:");
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String reportDate = df.format(surveyDate);
		sbData.append(reportDate);
		sbData.append(TAB_SEPERATOR);

		sbData.append("CampusLikes:");
		String sLikes = "";
		int nCount = 0;
		for (int i = 0; i < campusLikes.size(); i++)
		{
			nCount++;
			if(nCount == campusLikes.size())
			{
				sLikes += campusLikes.get(i);
			}
			else
			{
				sLikes += campusLikes.get(i);
				sLikes += COMMA_SEPERATOR;
			}
		}
		sbData.append(sLikes);
		sbData.append(TAB_SEPERATOR);

		sbData.append("HowInterested:");
		sbData.append(howInterested);
		sbData.append(TAB_SEPERATOR);

		sbData.append("Likelihood:");
		sbData.append(likelihood);
		sbData.append(TAB_SEPERATOR);

		sbData.append("Raffle:");
		sbData.append(raffle);
		sbData.append(TAB_SEPERATOR);

		sbData.append("Comments:");
		sbData.append(comments);
		sbData.append(TAB_SEPERATOR);

		return sbData.toString();
	}
}
