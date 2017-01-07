package com.sample.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@WebService()
public class SearchService
{
	@WebMethod
	public List<String> search( String fName,
								String lName,
								String city,
								String state)
	{
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement psSelectData =  null;
		PreparedStatement psECdata = null;
		List<String> lstSearchResult = null;
		boolean bFN = false;
		boolean bLN = false;
		boolean bCity = false;
		boolean bState = false;
		String sQuery = "";
		int nSurveyDataColCnt = 17;
		int nECDataColCnt = 4;

		try
		{
			System.out.println("Helllooo...");

			//Connect with the DB
			String DATASOURCE_CONTEXT = "java:jboss/datasources/mysqlds";

			Context initialContext = new InitialContext();
			ds = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
			if(ds == null)
			{
				throw new SQLException("Could not get the datasource");
			}
			connection = ds.getConnection();

			//String sQuery = createSearchQuery(fName, lName, city, state);
			//create search queries
			sQuery = "select * from mydb.test_table where ";

			if(fName.isEmpty() == false)
			{
				bFN = true;
				sQuery += "FIRST_NAME LIKE " + "'%" + fName + "%'";
			}
			if(lName.isEmpty() == false)
			{
				bLN = true;
				if(bFN == true)
				{
					sQuery += " OR LAST_NAME LIKE " + "'%" + lName + "%'";
				}
				else
				{
					sQuery += "LAST_NAME LIKE " + "'%" + lName + "%'";
				}
			}
			if(city.isEmpty() == false)
			{
				bCity = true;
				if(bFN == true || bLN == true)
				{
					sQuery += " OR CITY LIKE " + "'%" + city + "%'";
				}
				else
				{
					sQuery += "CITY LIKE " + "'%" + city + "%'";
				}
			}
			if(state.isEmpty() == false)
			{
				bState = true;
				if(bFN == true || bLN == true || bCity == true)
				{
					sQuery += " OR STATE LIKE " + "'%" + state + "%'";
				}
				else
				{
					sQuery += "STATE LIKE " + "'%" + state + "%'";
				}
			}

			String sECRecQryTemplate = "select * from emergency_contact where ec_id = %d";

			//Perform the search
			psSelectData = connection.prepareStatement(sQuery);
			ResultSet result =  psSelectData.executeQuery();

			lstSearchResult = new ArrayList<String>();

			while (result.next())
			{
				StringBuilder sb = new StringBuilder();

				for (int i = 1; i <= nSurveyDataColCnt; i++)
				{
					sb.append(result.getString(i));
					if(i != nSurveyDataColCnt)
					{
						sb.append("::");
					}
				}

				int nECid = Integer.parseInt(result.getString("EC_ID"));

				String sECRecordQuery = String.format(sECRecQryTemplate, nECid);

				psECdata = connection.prepareStatement(sECRecordQuery);
				ResultSet resultECData =  psECdata.executeQuery();

				while(resultECData.next())
				{
					for (int j=2; j <= nECDataColCnt; j++)
					{
						sb.append("::");
						sb.append(resultECData.getString(j));
					}
				}

				lstSearchResult.add(sb.toString());
			}

			return lstSearchResult;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
