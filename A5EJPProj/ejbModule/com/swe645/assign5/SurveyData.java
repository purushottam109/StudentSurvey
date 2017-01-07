package com.swe645.assign5;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="test_table")
public class SurveyData implements Serializable
{
	private static final long serialVersionUID = 1L;

	public SurveyData()
	{
		super();
	}

	@Id
	@Column(name="id")
	private long id;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="STREET_ADDR")
	private String streetAddress;

	@Column(name="ZIP_CODE")
	private String zipCode;

	@Column(name="CITY")
	private String city;

	@Column(name="STATE")
	private String state;

	@Column(name="TEL_NUM")
	private String telNum;

	@Column(name="EMAIL")
	private String email;

	@Column(name="DATE_OF_SURVEY")
	private Date dateOfSurvey;

	@Column(name="LIKES")
	private String likes;

	@Column(name="HOW_INTERESTED")
	private String howInterested;

	@Column(name="LIKELIHOOD")
	private String likelihood;

	@Column(name="RAFFLE_NUMS")
	private String raffleNums;

	@Column(name="COMMENTS")
	private String comments;

	@Column(name="SEM_START_DATE")
	private Date semStartDate;

	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="EC_ID", referencedColumnName="EC_ID")
	private EmergencyContact emergencyContact;

	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}


	public String getStreetAddress()
	{
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress)
	{
		this.streetAddress = streetAddress;
	}


	public String getZipCode()
	{
		return zipCode;
	}
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}


	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}


	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}


	public String getTelNum()
	{
		return telNum;
	}
	public void setTelNum(String telNum)
	{
		this.telNum = telNum;
	}


	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}


	public Date getDateOfSurvey()
	{
		return dateOfSurvey;
	}
	public void setDateOfSurvey(Date dateOfSurvey)
	{
		this.dateOfSurvey = dateOfSurvey;
	}


	public String getLikes()
	{
		return likes;
	}
	public void setLikes(String likes)
	{
		this.likes = likes;
	}


	public String getHowInterested()
	{
		return howInterested;
	}
	public void setHowInterested(String howInterested)
	{
		this.howInterested = howInterested;
	}


	public String getLikelihood()
	{
		return likelihood;
	}
	public void setLikelihood(String likelihood)
	{
		this.likelihood = likelihood;
	}


	public String getRaffleNums()
	{
		return raffleNums;
	}
	public void setRaffleNums(String raffleNums)
	{
		this.raffleNums = raffleNums;
	}


	public String getComments()
	{
		return comments;
	}
	public void setComments(String comments)
	{
		this.comments = comments;
	}


	public Date getSemStartDate()
	{
		return semStartDate;
	}
	public void setSemStartDate(Date semStartDate)
	{
		this.semStartDate = semStartDate;
	}

	//@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY, orphanRemoval=false)
	//@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	//@JoinColumn(name="EC_ID")
	//@PrimaryKeyJoinColumn
	//@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    //@JoinColumn(name="EC_ID")
	public EmergencyContact getEmergencyContact()
	{
		return emergencyContact;
	}
	public void setEmergencyContact(EmergencyContact emergencyContact)
	{
		this.emergencyContact = emergencyContact;
	}

	@Override
    public String toString()
	{
        String sReturnString = "SurveyData [id=" + id +
        					   ", fName=" + firstName +
        					   ", lName=" + lastName +
        					   ", StreetAddr=" + streetAddress +
        					   ", ZipCode=" + zipCode +
        					   ", City=" + city +
        					   ", State=" + state +
        					   ", TelNum=" + telNum +
        					   ", Email=" + email +
        					   ", SurveyDate=" + dateOfSurvey +
        					   ", Like=" + likes +
        					   ", How_Interested=" + howInterested +
        					   ", Likelihood=" + likelihood +
        					   ", RaffleNums=" + raffleNums +
        					   ", Comments=" + comments +
        					   ", SemStartDate=" + semStartDate +
        					   "]";

        return sReturnString;
    }

}
