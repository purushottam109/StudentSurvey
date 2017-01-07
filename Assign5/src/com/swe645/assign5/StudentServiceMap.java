package com.swe645.assign5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.sample.example.SearchService;
import com.sample.example.SearchServiceService;

public class StudentServiceMap implements StudentService
{
	private static final String BASEPATH = "/home/bitnami/Assignment5/";
	private static final String FILENAME = "surveyData.txt";
	private static final String TAB_SEPERATOR = "\t";
	private static final String COMMA_SEPERATOR = ",";
	private static final String INSERT_RECORD = "insert into mydb.test_table values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_RECORDS = "select * from mydb.test_table";
	private static final String GET_SPECIFIC_RECORDS = "";
	private static Context initialContext;
	private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";

	public static Context getInitialContext() throws NamingException
	{
        if (initialContext == null) {
            Properties properties = new Properties();
            properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);

            initialContext = new InitialContext(properties);
        }
        return initialContext;
    }


	private IA5EJBRemote getEJBInterface() throws NamingException
	{
		Context initialContext = new InitialContext();
		IA5EJBRemote objA4EJBRemote = (IA5EJBRemote)initialContext.lookup("java:global/A5EAR/A5EJPProj/A5EJBImpl!com.swe645.assign5.IA5EJBRemote");
		return objA4EJBRemote;
	}

	@Override
	public double computeMean(String sRaffleData)
	{
		List<Integer> lstRaffleData = convertRaffleData(sRaffleData);
		if(lstRaffleData == null)
		{
			return 0;
		}
		return calculateMean(lstRaffleData);
	}

	@Override
	public double computeStdDev(String sRaffleData)
	{
		List<Integer> lstRaffleData = convertRaffleData(sRaffleData);
		if(lstRaffleData == null)
		{
			return 0;
		}
		double dblMean = calculateMean(lstRaffleData);
		return calculateStdDeviation(lstRaffleData, dblMean);
	}

	@Override
	public void saveSurveyData(Student student)
	{
		try
		{
			IA5EJBRemote obj = getEJBInterface();
			SurveyData objSurveyData = new SurveyData();
			EmergencyContact objEmContact = new EmergencyContact();

			objEmContact.setEcName(student.getEcName());
			objEmContact.setEcEmail(student.getEcEmail());
			objEmContact.setEcTelNum(student.getEcTelNum());

			objSurveyData.setEmergencyContact(objEmContact);
			objSurveyData.setFirstName(student.getFirstName());
			objSurveyData.setLastName(student.getLastName());
			objSurveyData.setStreetAddress(student.getStreetAddress());
			objSurveyData.setCity(student.getCity());
			objSurveyData.setZipCode(student.getZipCode());
			objSurveyData.setState(student.getState());
			objSurveyData.setTelNum(student.getPhoneNumber());
			objSurveyData.setEmail(student.getEmail());
			objSurveyData.setDateOfSurvey(student.getSurveyDate());
			objSurveyData.setLikes(convertListToCommaSepString(student.getCampusLikes()));
			objSurveyData.setHowInterested(student.getHowInterested());
			objSurveyData.setLikelihood(student.getLikelihood());
			objSurveyData.setRaffleNums(student.getRaffle());
			objSurveyData.setComments(student.getComments());
			objSurveyData.setSemStartDate(student.getSemStartDate());
			obj.addSurveyData(objSurveyData);

		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getQueriedData(String sColumnName)
	{
		List<String> lstResult = null;
		String sSQLSelectQuery_Specific = "select distinct %s from mydb.test_table";
		try
		{
			String sQuery = String.format(sSQLSelectQuery_Specific, sColumnName);
			lstResult = getQueryData(sQuery);
			return lstResult;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Student> retrieveSurveyInfo()
	{
		List<Student> lstStudents = null;
		try
		{
			IA5EJBRemote obj = getEJBInterface();
			List<SurveyData> lstSurveyData = obj.retrieveSurveyInfo();
			lstStudents = new ArrayList<Student>();
			for (SurveyData surveyData : lstSurveyData)
			{
				lstStudents.add(convertSurveyDataToStudent(surveyData));
			}

			//lstStudents = readFromFile();
			//lstStudents = readFromDB_All();
			return lstStudents;
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Student> delete(Student objStudent)
	{
		List<Student> lstStudents = null;
		try
		{
			IA5EJBRemote obj = getEJBInterface();

			SurveyData objSurveyData = convertStudentToSurveyData(objStudent);

			obj.delete(objSurveyData);

			lstStudents = retrieveSurveyInfo();
			return lstStudents;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	private SearchData convertSearchSurveyToData(SearchSurvey objSearchSurvey)
	{
		SearchData objSearchData = null;
		try
		{
			objSearchData = new SearchData();

			objSearchData.setCity(objSearchSurvey.getCity());
			objSearchData.setFirstName(objSearchSurvey.getFirstName());
			objSearchData.setLastName(objSearchSurvey.getLastName());
			objSearchData.setState(objSearchSurvey.getState());

			return objSearchData;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Student> searchData(SearchSurvey objSearchSurvey)
	{
		/*
		List<Student> lstStudents = null;
		List<String> lstData = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		SearchServiceService objSrchSvcSvc = new SearchServiceService();
		SearchService objSrchSvc = objSrchSvcSvc.getSearchServicePort();
		try 
		{

			lstData = new ArrayList<String>();
			lstData = objSrchSvc.search(objSearchSurvey.getFirstName(),
										objSearchSurvey.getLastName(),
										objSearchSurvey.getCity(),
										objSearchSurvey.getState());
			
			lstStudents = new ArrayList<Student>();
			for (String sRec : lstData)
			{
				Student objStud = new Student();

				String[] lstRec = sRec.split("::");

				objStud.setId(Integer.parseInt(lstRec[0]));
				objStud.setFirstName(lstRec[1]);
				objStud.setLastName(lstRec[2]);
				objStud.setStreetAddress(lstRec[3]);
				objStud.setZipCode(lstRec[4]);
				objStud.setCity(lstRec[5]);
				objStud.setState(lstRec[6]);
				objStud.setPhoneNumber(lstRec[7]);
				objStud.setEmail(lstRec[8]);
				try {
					objStud.setSurveyDate(formatter.parse(lstRec[9]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				String[] arrLikes = lstRec[10].split(",");
				List<String> lstLikes = new ArrayList<String>();
				for (int i = 0; i < arrLikes.length; i++) {
					lstLikes.add(arrLikes[i]);
				}
				
				objStud.setCampusLikes(lstLikes);
				objStud.setHowInterested(lstRec[11]);
				objStud.setLikelihood(lstRec[12]);
				objStud.setRaffle(lstRec[13]);
				objStud.setComments(lstRec[14]);
				try {
					objStud.setSemStartDate(formatter.parse(lstRec[15]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				objStud.setEcid(Integer.parseInt(lstRec[16]));
				objStud.setEcName(lstRec[17]);
				objStud.setEcTelNum(lstRec[18]);
				objStud.setEcEmail(lstRec[19]);
				
				lstStudents.add(objStud);
			}
			
			return lstStudents;
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;*/
		
		
		List<Student> lstStudents = null;
		try
		{
			SearchData objSearchData = convertSearchSurveyToData(objSearchSurvey);

			IA5EJBRemote obj = getEJBInterface();
			List<SurveyData> lstSurveyData = obj.searchData(objSearchData);
			lstStudents = new ArrayList<Student>();
			for (SurveyData surveyData : lstSurveyData)
			{
				lstStudents.add(convertSurveyDataToStudent(surveyData));
			}
			return lstStudents;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	private SurveyData convertStudentToSurveyData(Student objStudent)
	{
		SurveyData objSurveyData = null;
		try
		{
			objSurveyData = new SurveyData();
			EmergencyContact objEmContct = new EmergencyContact();

			objEmContct.setEcName(objStudent.getEcName());
			objEmContct.setEcTelNum(objStudent.getEcTelNum());
			objEmContct.setEcEmail(objStudent.getEcEmail());
			objEmContct.setEcid(objStudent.getEcid());

			objSurveyData.setCity(objStudent.getCity());
			objSurveyData.setComments(objStudent.getComments());
			objSurveyData.setDateOfSurvey(objStudent.getSurveyDate());
			objSurveyData.setEmail(objStudent.getEmail());
			objSurveyData.setEmergencyContact(objEmContct);
			objSurveyData.setFirstName(objStudent.getFirstName());
			objSurveyData.setHowInterested(objStudent.getHowInterested());
			objSurveyData.setId(objStudent.getId());
			objSurveyData.setLastName(objStudent.getLastName());
			objSurveyData.setLikelihood(objStudent.getLikelihood());

			String likes = convertListToCommaSepString(objStudent.getCampusLikes());
			objSurveyData.setLikes(likes);
			objSurveyData.setRaffleNums(objStudent.getRaffle());
			objSurveyData.setSemStartDate(objStudent.getSemStartDate());
			objSurveyData.setState(objStudent.getState());
			objSurveyData.setStreetAddress(objStudent.getStreetAddress());
			objSurveyData.setTelNum(objStudent.getPhoneNumber());
			objSurveyData.setZipCode(objStudent.getZipCode());

			return objSurveyData;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	private Student convertSurveyDataToStudent(SurveyData objSurveyData)
	{
		Student objStudent = null;
		try
		{
			objStudent = new Student();

			objStudent.setId(objSurveyData.getId());
			objStudent.setFirstName(objSurveyData.getFirstName());
			objStudent.setLastName(objSurveyData.getLastName());
			objStudent.setStreetAddress(objSurveyData.getStreetAddress());
			objStudent.setZipCode(objSurveyData.getZipCode());
			objStudent.setCity(objSurveyData.getCity());
			objStudent.setState(objSurveyData.getState());
			objStudent.setPhoneNumber(objSurveyData.getTelNum());

			String[] arsLikes = objSurveyData.getLikes().split(COMMA_SEPERATOR);
			List<String> lstLikes = new ArrayList<String>();
			for (int j = 0; j < arsLikes.length; j++)
			{
				lstLikes.add(arsLikes[j]);
			}
			objStudent.setCampusLikes(lstLikes);

			objStudent.setComments(objSurveyData.getComments());
			objStudent.setHowInterested(objSurveyData.getHowInterested());
			objStudent.setLikelihood(objSurveyData.getLikelihood());
			objStudent.setRaffle(objSurveyData.getRaffleNums());
			objStudent.setSemStartDate(objSurveyData.getSemStartDate());
			objStudent.setSurveyDate(objSurveyData.getDateOfSurvey());
			objStudent.setEmail(objSurveyData.getEmail());

			//Emergency Contact Info
			objStudent.setEcName(objSurveyData.getEmergencyContact().getEcName());
			objStudent.setEcEmail(objSurveyData.getEmergencyContact().getEcEmail());
			objStudent.setEcTelNum(objSurveyData.getEmergencyContact().getEcTelNum());
			objStudent.setEcid(objSurveyData.getEmergencyContact().getEcid());

			return objStudent;

		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	private String createSearchQuery(SearchSurvey objSearchSurvey)
	{
		boolean bFN = false;
		boolean bLN = false;
		boolean bCity = false;
		boolean bState = false;
		String sQuery = "";
		try
		{
			sQuery = "select * from mydb.test_table where ";

			if(objSearchSurvey.getFirstName().isEmpty() == false)
			{
				bFN = true;
				sQuery += "FIRST_NAME LIKE " + "'%" + objSearchSurvey.getFirstName() + "%'";
			}
			if(objSearchSurvey.getLastName().isEmpty() == false)
			{
				bLN = true;
				if(bFN == true)
				{
					sQuery += " OR LAST_NAME LIKE " + "'%" + objSearchSurvey.getLastName() + "%'";
				}
				else
				{
					sQuery += "LAST_NAME LIKE " + "'%" + objSearchSurvey.getLastName() + "%'";
				}
			}
			if(objSearchSurvey.getCity().isEmpty() == false)
			{
				bCity = true;
				if(bFN == true || bLN == true)
				{
					sQuery += " OR CITY LIKE " + "'%" + objSearchSurvey.getCity() + "%'";
				}
				else
				{
					sQuery += "CITY LIKE " + "'%" + objSearchSurvey.getCity() + "%'";
				}
			}
			if(objSearchSurvey.getState().isEmpty() == false)
			{
				bState = true;
				if(bFN == true || bLN == true || bCity == true)
				{
					sQuery += " OR STATE LIKE " + "'%" + objSearchSurvey.getState() + "%'";
				}
				else
				{
					sQuery += "STATE LIKE " + "'%" + objSearchSurvey.getState() + "%'";
				}
			}

			return sQuery;
		}
		catch (Exception ex)
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return null;
	}

	private boolean saveToDB(Student student) throws SQLException
	{
		StudentSurvey objStudentSurvey = null;
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement psInsertData =  null;
		String sSQLInsertQuery = "";
		try
		{
			objStudentSurvey = new StudentSurvey();

			ds = objStudentSurvey.getDataSource();
			if(ds == null)
			{
				throw new SQLException("Could not get the datasource");
			}

			//obtain connection from the connection pool
			connection = ds.getConnection();
			if(connection == null)
			{
				throw new SQLException("Unable to connect to datasourec");
			}

			connection.setAutoCommit(false);

			sSQLInsertQuery = INSERT_RECORD;
			psInsertData = connection.prepareStatement(sSQLInsertQuery);
			psInsertData.setString(1, student.getFirstName());
			psInsertData.setString(2, student.getLastName());
			psInsertData.setString(3, student.getStreetAddress());
			psInsertData.setString(4, student.getZipCode());
			psInsertData.setString(5, student.getCity());
			psInsertData.setString(6, student.getState());
			psInsertData.setString(7, student.getPhoneNumber());
			psInsertData.setString(8, student.getEmail());
			psInsertData.setDate(9, convertUtilToSql(student.getSurveyDate()));
			psInsertData.setString(10, convertListToCommaSepString(student.getCampusLikes()));
			psInsertData.setString(11, student.getHowInterested());
			psInsertData.setString(12, student.getLikelihood());
			psInsertData.setString(13, student.getRaffle());
			psInsertData.setString(14, student.getComments());
			psInsertData.setDate(15, convertUtilToSql(student.getSemStartDate()));

			psInsertData.executeUpdate();

			connection.commit();

			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			if(connection != null)
			{
				connection.rollback();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			if(connection != null)
			{
				connection.rollback();
			}
		}
		finally
		{
			if (psInsertData != null)
			{
				psInsertData.close();
			}
			if (connection != null)
			{
				connection.close();
			}
		}
		return false;
	}

	private List<String> getQueryData(String sQuery) throws SQLException
	{
		StudentSurvey objStudentSurvey = null;
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement psSelectData =  null;
		List<String> lstResult = null;

		try
		{
			objStudentSurvey = new StudentSurvey();

			ds = objStudentSurvey.getDataSource();
			if(ds == null)
			{
				throw new SQLException("Could not get the datasource");
			}

			//obtain connection from the connection pool
			connection = ds.getConnection();
			if(connection == null)
			{
				throw new SQLException("Unable to connect to datasourec");
			}

			psSelectData = connection.prepareStatement(sQuery);
			ResultSet result =  psSelectData.executeQuery();

			lstResult = new ArrayList<String>();

			while (result.next())
			{
				String sData = result.getString(1);
				lstResult.add(sData);
			}
			return lstResult;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			if(connection != null)
			{
				connection.rollback();
			}
		}
		finally
		{
			if (psSelectData != null)
			{
				psSelectData.close();
			}
			if (connection != null)
			{
				connection.close();
			}
		}
		return null;
	}

	private List<Student> readFromDB_All() throws SQLException
	{
		StudentSurvey objStudentSurvey = null;
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement psSelectData =  null;
		String sSQLSelectQuery_All = "";
		List<Student> lstStudents = null;
		try
		{
			objStudentSurvey = new StudentSurvey();

			ds = objStudentSurvey.getDataSource();
			if(ds == null)
			{
				throw new SQLException("Could not get the datasource");
			}

			//obtain connection from the connection pool
			connection = ds.getConnection();
			if(connection == null)
			{
				throw new SQLException("Unable to connect to datasourec");
			}

			lstStudents = new ArrayList<Student>();

			sSQLSelectQuery_All = GET_ALL_RECORDS;
			psSelectData = connection.prepareStatement(sSQLSelectQuery_All);
			ResultSet result =  psSelectData.executeQuery();

			while (result.next())
			{
				Student objStudent = new Student();

				objStudent.setFirstName(result.getString("FIRST_NAME"));
				objStudent.setLastName(result.getString("LAST_NAME"));
				objStudent.setStreetAddress(result.getString("STREET_ADDR"));
				objStudent.setZipCode(result.getString("ZIP_CODE"));
				objStudent.setCity(result.getString("CITY"));
				objStudent.setState(result.getString("STATE"));
				objStudent.setPhoneNumber(result.getString("TEL_NUM"));
				objStudent.setEmail(result.getString("EMAIL"));
				objStudent.setSurveyDate(result.getDate("DATE_OF_SURVEY"));

				String[] arsLikes = result.getString("LIKES").split(COMMA_SEPERATOR);
				List<String> lstLikes = new ArrayList<String>();
				for (int j = 0; j < arsLikes.length; j++)
				{
					lstLikes.add(arsLikes[j]);
				}

				objStudent.setCampusLikes(lstLikes);
				objStudent.setHowInterested(result.getString("HOW_INTERESTED"));
				objStudent.setLikelihood(result.getString("LIKELIHOOD"));
				objStudent.setRaffle(result.getString("RAFFLE_NUMS"));
				objStudent.setComments(result.getString("COMMENTS"));
				objStudent.setSemStartDate(result.getDate("SEM_START_DATE"));

				lstStudents.add(objStudent);
			}

			return lstStudents;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			if(connection != null)
			{
				connection.rollback();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			if(connection != null)
			{
				connection.rollback();
			}
		}
		finally
		{
			if (psSelectData != null)
			{
				psSelectData.close();
			}
			if (connection != null)
			{
				connection.close();
			}
		}
		return null;
	}

	private static java.sql.Date convertUtilToSql(java.util.Date uDate)
	{
		try
		{
			java.sql.Date sDate = new java.sql.Date(uDate.getTime());
			return sDate;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	private static String convertListToCommaSepString(List<String> lstInput)
	{
		String sResult = "";
		StringBuilder sb = null;
		try
		{
			sb = new StringBuilder();

			int nCnt = 0;
			for (String string : lstInput)
			{
				if(nCnt == 0)
				{
					sb.append(string);
				}
				else
				{
					sb.append(",");
					sb.append(string);
				}
				nCnt++;
			}
			sResult = sb.toString();
			return sResult;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	private double calculateMean(List<Integer> lstRaffleData)
	{
		double dblSum = 0;
		double dblMean = 0;
		try
		{
			for (int i = 0; i < lstRaffleData.size(); i++)
			{
				dblSum += lstRaffleData.get(i);
			}
			dblMean = dblSum/lstRaffleData.size();
			return dblMean;
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	private double calculateStdDeviation(List<Integer> lstRaffleData, double dblMean)
	{
		double dblStdDev = 0;
		try
		{
			double partialSum = 0;
			for (int i = 0; i < lstRaffleData.size(); i++)
			{
				partialSum += (lstRaffleData.get(i) - dblMean)*(lstRaffleData.get(i) - dblMean);
			}
			dblStdDev = Math.sqrt(partialSum/lstRaffleData.size());
			return dblStdDev;
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	private boolean writeToFile(Student student)
	{
		File fout;
		FileOutputStream fos;
		BufferedWriter bw;
		try
		{
			fout = new File(BASEPATH + FILENAME);
			fos = new FileOutputStream(fout, true);
			bw = new BufferedWriter(new OutputStreamWriter(fos));

			bw.write(student.toString());
			bw.newLine();

			try
			{
				bw.close();
			}
			catch (Exception e)
			{
				return false;
			}
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	private List<Student> readFromFile()
	{
		List<Student> lstStudentData = null;
		File fin;
		FileInputStream fis = null;
		BufferedReader br = null;
		try
		{
			fin = new File(BASEPATH + FILENAME);
			fis = new FileInputStream(fin);

			//Construct BufferedReader from InputStreamReader
			br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			lstStudentData = new ArrayList<Student>();
			while ((line = br.readLine()) != null)
			{
				//System.out.println(line);
				Student objStudent = convertStringToStudent(line);
				if(objStudent != null)
				{
					lstStudentData.add(objStudent);
				}
			}

			br.close();

			return lstStudentData;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static boolean isMissing(String value)
	{
		return ((value==null) || (value.trim().isEmpty()));
	}

	private static Student convertStringToStudent(String sData)
	{
		Student objStudent = null;
		String[] arsStudentData = null;
		try
		{
			arsStudentData = sData.split(TAB_SEPERATOR);
			objStudent = new Student();

			for (int i = 0; i < arsStudentData.length; i++) {
				String[] arsSplitField = arsStudentData[i].split(":", 2);

				String sTag = arsSplitField[0];
				String sInfo = arsSplitField[1];

				if(sTag.equals("FirstName"))
				{
					objStudent.setFirstName(sInfo);
				}

				if(sTag.equals("LastName"))
				{
					objStudent.setLastName(sInfo);
				}

				if(sTag.equals("StreetAddress"))
				{
					objStudent.setStreetAddress(sInfo);
				}

				if(sTag.equals("City"))
				{
					objStudent.setCity(sInfo);
				}

				if(sTag.equals("State"))
				{
					objStudent.setState(sInfo);
				}

				if(sTag.equals("ZipCode"))
				{
					objStudent.setZipCode(sInfo);
				}

				if(sTag.equals("TelephoneNumber"))
				{
					objStudent.setPhoneNumber(sInfo);
				}

				if(sTag.equals("Email"))
				{
					objStudent.setEmail(sInfo);
				}

				if(sTag.equals("DateOfSurvey"))
				{
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					Date surveyDate = df.parse(sInfo);
					objStudent.setSurveyDate(surveyDate);
				}

				if(sTag.equals("CampusLikes"))
				{
					String[] arsLikes = sInfo.split(COMMA_SEPERATOR);
					List<String> lstLikes = new ArrayList<String>();
					for (int j = 0; j < arsLikes.length; j++)
					{
						lstLikes.add(arsLikes[j]);
					}
					objStudent.setCampusLikes(lstLikes);
				}

				if(sTag.equals("HowInterested"))
				{
					objStudent.setHowInterested(sInfo);
				}

				if(sTag.equals("Likelihood"))
				{
					objStudent.setLikelihood(sInfo);
				}

				if(sTag.equals("Raffle"))
				{
					objStudent.setRaffle(sInfo);
				}

				if(sTag.equals("Comments"))
				{
					objStudent.setComments(sInfo);
				}
			}
			return objStudent;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static List<Integer> convertRaffleData(String sRaffleData)
	{
		String[] arsRaffle;
		List<Integer> lstRaffleData = null;
		try
		{
			if(isMissing(sRaffleData))
			{
				return null;
			}
			arsRaffle = sRaffleData.split(",");
			lstRaffleData = new ArrayList<Integer>();
			for (int i = 0; i < arsRaffle.length; i++)
			{
				Integer val = Integer.parseInt(arsRaffle[i]);
				lstRaffleData.add(val);
			}
			return lstRaffleData;
		}
		catch (Exception e)
		{
			return null;
		}
	}

}
