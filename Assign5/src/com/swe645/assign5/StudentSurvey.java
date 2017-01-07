package com.swe645.assign5;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.validator.ValidatorException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.primefaces.context.RequestContext;

import com.sun.faces.component.visit.FullVisitContext;


@ManagedBean(name = "studentSurvey")
@SessionScoped
public class StudentSurvey implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final String EMAIL_PATTERN = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.(com|net|org|edu)";
	private static final String PHONE_PATTERN = "^\\s*[(]{1}(\\d{3})[)]{1}[-]{1}(\\d{3})[-. ]*(\\d{4})\\s*$";

	private static final String likesString = "Students,Location,Campus,Atmosphere,Dorm Rooms,Sports";
	private static final String[] likesArray = likesString.split(",");

	private static final String howInterestedString = "Friends,Television,Internet,Other";
	private static final String[] howInterestedArray = howInterestedString.split(",");

	private static final String likelihoodString = "Very Likely,Likely,Unlikely";
	private static final String[] likelihoodArray = likelihoodString.split(",");

	private static StudentService studentService = new StudentServiceMap();

	//Allow server to inject datasource
	@Resource(mappedName="java:jboss/datasources/mysqlds")
	private static DataSource dataSource;

	public DataSource getDataSource()
	{
		DataSource datasource = null;
		//return dataSource;
		String DATASOURCE_CONTEXT = "java:jboss/datasources/mysqlds";

	    Connection result = null;
	    try {
	      Context initialContext = new InitialContext();
	      /*if ( initialContext == null){
	        System.out.println("JNDI problem. Cannot get InitialContext.");
	      }*/
	      datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
	      if (datasource != null) {
	        result = datasource.getConnection();
	      }
	      else {
	        System.out.println("Failed to lookup datasource.");
	      }

	      //IA4EJB objA4EJB = (IA4EJB)initialContext.lookup("java:global/Assign4EAR/A4EJBProj/A4EJBImplA!com.swe645.assign5.IA4EJB");


	      return datasource;
	    }
	    catch ( NamingException ex ) {
	    	System.out.println("Cannot get connection: " + ex);
	      }
	      catch(SQLException ex){
	    	  System.out.println("Cannot get connection: " + ex);
	      }
	    return datasource;
	}

	private Student studentData;
	private SearchSurvey searchSurvey;
	private WinningResult winningResult;
	private List<Student> surveyList;
	private List<Student> searchSurveyList;

	/*
	//private String city;
	//private String state;
	//private String zipCode;
	private boolean isCityListDisabled = true;


	public String getCity() {
		System.out.println("Get City : " + studentData.getCity());
		return studentData.getCity();
	}

	public void setCity(String city) {
		System.out.println("Set City : " + studentData.getCity());
		studentData.setCity(city.trim());
	}

	public String getZipCode() {
		return studentData.getZipCode();
	}

	public void setZipCode(String zipCode)
	{
		studentData.setZipCode(zipCode.trim());
		studentData.setCity("");
		isCityListDisabled = false;
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
		return(ZipInfo.ZIP_MAP.get(studentData.getZipCode()));
	}*/

	public SearchSurvey getSearchSurvey() {
		return searchSurvey;
	}

	public void setSearchSurvey(SearchSurvey searchSurvey) {
		this.searchSurvey = searchSurvey;
	}

	public List<Student> getSurveyList() {
		return surveyList;
	}

	public void setSurveyList(List<Student> surveyList) {
		this.surveyList = surveyList;
	}

	public List<Student> getSearchSurveyList() {
		return searchSurveyList;
	}

	public void setSearchSurveyList(List<Student> searchSurveyList) {
		this.searchSurveyList = searchSurveyList;
	}

	public StudentSurvey()
	{
		studentData = new Student();
		searchSurvey = new SearchSurvey();
		winningResult = new WinningResult();
		System.out.println("StudentSurvey started..!!");
	}

	/**
	 * @return the studentData
	 */
	public Student getStudentData() {
		return studentData;
	}

	/**
	 * @param studentData the studentData to set
	 */
	public void setStudentData(Student studentData) {
		this.studentData = studentData;
	}

	public WinningResult getWinningResult() {
		return winningResult;
	}

	public void setWinningResult(WinningResult winningResult) {
		this.winningResult = winningResult;
	}

	private boolean isMissing(String value)
	{
		return ((value==null) || (value.trim().isEmpty()));
	}

	public UIComponent findComponent(final String id) {

	    FacesContext context = FacesContext.getCurrentInstance();
	    UIViewRoot root = context.getViewRoot();
	    final UIComponent[] found = new UIComponent[1];

	    root.visitTree(new FullVisitContext(context), new VisitCallback() {
	        @Override
	        public VisitResult visit(VisitContext context, UIComponent component) {
	            if(component.getId().equals(id)){
	                found[0] = component;
	                return VisitResult.COMPLETE;
	            }
	            return VisitResult.ACCEPT;
	        }
	    });

	    return found[0];

	}


	public String getCityFrmMap(Map<String, String> mapCities, String sState)
	{
		try
		{
			for (String o : mapCities.keySet())
			{
				if (mapCities.get(o).equals(sState))
				{
					return o;
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public void updateDOM()
	{
	      RequestContext.getCurrentInstance().execute("getSelectedText()");
	}

	public List<Student> searchData()
	{
		try
		{
			searchSurveyList = null;
			searchSurveyList = studentService.searchData(this.searchSurvey);
			return searchSurveyList;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public String saveData()
	{
		UIComponent txtState = findComponent("txtState");
		String sState = (String)((javax.faces.component.html.HtmlOutputText)txtState).getValue();
		studentData.setState(sState);

		UIComponent txtCity = findComponent("hiddenCity");
		String sCity = (String) ((javax.faces.component.html.HtmlInputHidden)txtCity).getValue();
		studentData.setCity(sCity);

		studentService.saveSurveyData(studentData);
		double mean = studentService.computeMean(studentData.getRaffle());
		double stdDev = studentService.computeStdDev(studentData.getRaffle());

		winningResult.setMean(mean);
		winningResult.setStdDeviation(stdDev);

		if(mean > 90)
		{
			return ("WinnerAcknowledgement");
		}
		return ("SimpleAcknowledgement");
	}

	public String retrieveSurveyData()
	{
		surveyList = studentService.retrieveSurveyInfo();
		return ("ListSurvey");
	}

	public String delete(Student objStudent) throws IOException
	{
		surveyList = studentService.delete(objStudent);
		 //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		 //ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		return ("SearchSurvey");
	}

	public String goToStudentSurveyPage()
	{
		return ("StudentSurveyPage");
	}

	public void validateEmailAddress(FacesContext context,
									 UIComponent componentToValidate,
									 Object value) throws ValidatorException
	{
		Pattern pattern;
		Matcher matcher;

		String sEmail = "";
		sEmail = ((String)value).trim();

		pattern = Pattern.compile(EMAIL_PATTERN);

		matcher = pattern.matcher(sEmail);
		if(matcher.matches() == false)
		{
			FacesMessage message = new FacesMessage("Invalid email format.");
			throw new ValidatorException(message);
		}

	}

	protected Date convertToDate(FacesContext context, org.primefaces.component.calendar.Calendar pfCalendarComponent, String submittedValue) {
        Locale locale = pfCalendarComponent.calculateLocale(context);
        SimpleDateFormat format = new SimpleDateFormat(pfCalendarComponent.getPattern(), locale);
        format.setTimeZone(pfCalendarComponent.calculateTimeZone());

        try {
            return format.parse(submittedValue);
        } catch (ParseException e) {
            throw new ConverterException(e);
        }
    }

	public void dateAfter(FacesContext context,
						  UIComponent componentToValidate,
						  Object value) throws ValidatorException
	{
		Date dtSemStartDate = ((Date)value);

		Object surveyDateValue = componentToValidate.getAttributes().get("dateOfSurvey");
		Date dtSurveyDate = (Date) ((org.primefaces.component.calendar.Calendar)surveyDateValue).getValue();
		if(dtSurveyDate.after(dtSemStartDate))
		{
			FacesMessage message = new FacesMessage("Semester Start date cannot be before Survey date.");
			throw new ValidatorException(message);
		}

	}


	public void validatePhoneNumber(FacesContext context,
									UIComponent componentToValidate,
									Object value) throws ValidatorException
	{
		Pattern pattern;
		Matcher matcher;

		String sPhNumber = "";
		sPhNumber = ((String)value).trim();

		pattern = Pattern.compile(PHONE_PATTERN);

		matcher = pattern.matcher(sPhNumber);
		if(matcher.matches() == false)
		{
			FacesMessage message = new FacesMessage("Phone number should be in (XXX)-XXX-XXXX format.");
			throw new ValidatorException(message);
		}
	}

	public void validateRaffleNumbers(FacesContext context,
								      UIComponent componentToValidate,
								      Object value) throws ValidatorException
	{
		String[] arsRaffleNos;
		if(isMissing((String)value) == false)
		{
			arsRaffleNos = ((String)value).split(",");
			if(arsRaffleNos.length < 10)
			{
				FacesMessage message = new FacesMessage("Enter atleast 10 comma seperated numbers numbers.");
				throw new ValidatorException(message);
			}
			for (int i = 0; i < arsRaffleNos.length; i++)
			{
				int nNum;
				try
				{
					nNum = Integer.parseInt(arsRaffleNos[i]);
				}
				catch (NumberFormatException e)
				{
					FacesMessage message = new FacesMessage("Please enter only numbers.");
					throw new ValidatorException(message);
				}
				if(nNum < 1 || nNum > 100)
				{
					FacesMessage message = new FacesMessage("Please enter numbers ranging from 1 through 100.");
					throw new ValidatorException(message);
				}
			}
		}
	}

	/*
	public List<String> completeHowInterested(String query)
	{
		List<String> match = new ArrayList<String>();
		HowInterested[] howInterest = HowInterested.values();
		for (int i = 0; i < howInterest.length; i++) {
			if(howInterest[i].toString().contains(query))
			{
				match.add(howInterest[i].toString());
			}
		}
		return match;
	}*/

	public List<String> completeLikes(String query)
	{
	    List<String> match = new ArrayList<String>();
	    for(String possibleLikes : likesArray)
		{
			if(possibleLikes.toLowerCase().contains(query.toLowerCase()))
			{
				match.add(possibleLikes);
			}
		}
		return match;
	}

	public List<String> completeHowInterested(String query)
	{
		List<String> match = new ArrayList<String>();
		for(String possibleHowInterested : howInterestedArray)
		{
			if(possibleHowInterested.toLowerCase().contains(query.toLowerCase()))
			{
				match.add(possibleHowInterested);
			}
		}
		return match;
	}

	public List<String> completeLikelihood(String query)
	{
		List<String> match = new ArrayList<String>();
		for(String possibleLikelihood : likelihoodArray)
		{
			if(possibleLikelihood.toLowerCase().contains(query.toLowerCase()))
			{
				match.add(possibleLikelihood);
			}
		}
		return match;
	}

	public List<String> completeSearchFirstName(String query)
	{
		String sColumnName = "FIRST_NAME";
		List<String> lstFirstNames = studentService.getQueriedData(sColumnName);

		List<String> match = new ArrayList<String>();
		for(String possibleFirstNames : lstFirstNames)
		{
			if(possibleFirstNames.toLowerCase().contains(query.toLowerCase()))
			{
				match.add(possibleFirstNames);
			}
		}
		return match;
	}

	public List<String> completeSearchLastName(String query)
	{
		String sColumnName = "LAST_NAME";
		List<String> lstLastNames = studentService.getQueriedData(sColumnName);

		List<String> match = new ArrayList<String>();
		for(String possibleLastNames : lstLastNames)
		{
			if(possibleLastNames.toLowerCase().contains(query.toLowerCase()))
			{
				match.add(possibleLastNames);
			}
		}
		return match;
	}

	public List<String> completeSearchCity(String query)
	{
		String sColumnName = "CITY";
		List<String> lstCities = studentService.getQueriedData(sColumnName);

		List<String> match = new ArrayList<String>();
		for(String possibleCities : lstCities)
		{
			if(possibleCities.toLowerCase().contains(query.toLowerCase()))
			{
				match.add(possibleCities);
			}
		}
		return match;
	}

	public List<String> completeSearchState(String query)
	{
		String sColumnName = "STATE";
		List<String> lstStates = studentService.getQueriedData(sColumnName);

		List<String> match = new ArrayList<String>();
		for(String possibleStates : lstStates)
		{
			if(possibleStates.toLowerCase().contains(query.toLowerCase()))
			{
				match.add(possibleStates);
			}
		}
		return match;
	}


	public ResultSet getData() throws SQLException
	{
		Connection connection = null;
		PreparedStatement getData =  null;
		try
		{
			// check datasource injected by server
			dataSource = getDataSource();
			if(dataSource == null)
			{
				throw new SQLException("Unable to obtain Datasourece");
			}

			//obtain connection from the connection pool
			connection = dataSource.getConnection();

			if(connection == null)
			{
				throw new SQLException("Unable to connect to datasourec");
			}

			String sql = "Select * from comments";
			getData = connection.prepareStatement(sql);

			ResultSet result = getData.executeQuery();
			System.out.println(result);
			int n = 56;
			return result;

			/*
			rowSet = new CachedRowSetImpl();
			rowSet.populate(getData.executeQuery());
			System.out.println(rowSet);
			return rowSet;
			*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			connection.close();
		}
		return null;

	}

}
