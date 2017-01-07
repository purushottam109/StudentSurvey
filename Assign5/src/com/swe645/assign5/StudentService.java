package com.swe645.assign5;

import java.util.List;

public interface StudentService
{
	public void saveSurveyData(Student student);

	public List<Student> retrieveSurveyInfo();

	public double computeMean(String sRaffleData);

	public double computeStdDev(String sRaffleData);

	public List<String> getQueriedData(String sColumnName);

	public List<Student> searchData(SearchSurvey objSearchSurvey);

	public List<Student> delete(Student objStudent);
}
