package com.swe645.assign5;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface IA5EJBRemote
{
	public void addSurveyData(SurveyData objSurveyData);

	public List<SurveyData> retrieveSurveyInfo();

	public List<SurveyData> getQueriedData(String sColumnName);

	public List<SurveyData> searchData(SearchData objSearchData);

	public boolean delete(SurveyData objSurveyData);

}
