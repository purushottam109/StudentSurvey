package com.swe645.assign5;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="emergency_contact")
public class EmergencyContact implements Serializable
{
	private static final long serialVersionUID = 1L;

	public EmergencyContact()
	{
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EC_ID")
	private long ecid;

	@Column(name="EC_NAME")
	private String ecName;

	@Column(name="EC_TEL_NUM")
	private String ecTelNum;

	@Column(name="EC_EMAIL")
	private String ecEmail;


	public long getEcid()
	{
		return ecid;
	}
	public void setEcid(long ecid)
	{
		this.ecid = ecid;
	}


	public String getEcName()
	{
		return ecName;
	}
	public void setEcName(String ecName)
	{
		this.ecName = ecName;
	}


	public String getEcTelNum()
	{
		return ecTelNum;
	}
	public void setEcTelNum(String ecTelNum)
	{
		this.ecTelNum = ecTelNum;
	}


	public String getEcEmail()
	{
		return ecEmail;
	}
	public void setEcEmail(String ecEmail)
	{
		this.ecEmail = ecEmail;
	}

}
