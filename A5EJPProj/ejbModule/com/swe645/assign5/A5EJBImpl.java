package com.swe645.assign5;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class A5EJBImpl
 */
@Stateless(mappedName = "A5EJBImpl")
public class A5EJBImpl implements IA5EJBRemote
{
	@PersistenceContext(unitName = "JPADB")
    private EntityManager entityManager;

    /**
     * Default constructor.
     */
    public A5EJBImpl() {    }

	@Override
	public void addSurveyData(SurveyData objSurveyData)
	{
		/*
		entityManager.persist(objSurveyData.getEmergencyContact());
		entityManager.flush();
		System.out.println(objSurveyData.getEmergencyContact().getEcid());
		entityManager.persist(objSurveyData);
		entityManager.flush();
		System.out.println(objSurveyData.getId());
		*/
		try
		{
			//entityManager.getTransaction().begin();
			EmergencyContact objEmContact = objSurveyData.getEmergencyContact();
			entityManager.persist(objEmContact);
			entityManager.flush();
			entityManager.persist(objSurveyData);
			entityManager.flush();

			//entityManager.getTransaction().commit();
		}
		catch (Exception e)
		{
			//entityManager.getTransaction().rollback();
	        e.printStackTrace();
		}

		//entityManager.flush();
		//entityManager.persist(objSurveyData);
		//System.out.println(objSurveyData.getId());
	}

	@Override
	public List<SurveyData> retrieveSurveyInfo()
	{
		String q = "SELECT p from " + SurveyData.class.getName() + " p";
        Query query = entityManager.createQuery(q);
        List<SurveyData> surveyData = query.getResultList();
        return surveyData;
	}

	@Override
	public boolean delete(SurveyData objSurveyData)
	{
		SurveyData objSD = entityManager.find(SurveyData.class, objSurveyData.getId());
		entityManager.remove(objSD);
		return true;
	}

	@Override
	public List<SurveyData> getQueriedData(String sColumnName)
	{
		return null;
	}

	@Override
	public List<SurveyData> searchData(SearchData objSearchData)
	{
		List<SurveyData> lstSurveyData = null;
		String sVal = "";
		try
		{
			String queryString = createQueryString(objSearchData);
			Query query = entityManager.createQuery(queryString);

			if(objSearchData.getFirstName().isEmpty() == false)
			{
				sVal = "%" + objSearchData.getFirstName() + "%";
				query.setParameter("firstName", sVal);
			}
			if(objSearchData.getLastName().isEmpty() == false)
			{
				sVal = "%" + objSearchData.getLastName() + "%";
				query.setParameter("lastName", sVal);
			}

			if(objSearchData.getCity().isEmpty() == false)
			{
				sVal = "%" + objSearchData.getCity() + "%";
				query.setParameter("city", sVal);
			}

			if(objSearchData.getState().isEmpty() == false)
			{
				sVal = "%" + objSearchData.getState() + "%";
				query.setParameter("state", sVal);
			}
			lstSurveyData = query.getResultList();
			return lstSurveyData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private String createQueryString(SearchData objSearchData)
	{
		boolean bFN = false;
		boolean bLN = false;
		boolean bCity = false;
		boolean bState = false;
		String sQuery = "";
		try
		{
			sQuery = "SELECT p from " + SurveyData.class.getName() + " p WHERE ";

			if(objSearchData.getFirstName().isEmpty() == false)
			{
				bFN = true;
				sQuery += "p.firstName LIKE " + ":firstName";
			}
			if(objSearchData.getLastName().isEmpty() == false)
			{
				bLN = true;
				if(bFN == true)
				{
					sQuery += " OR p.lastName LIKE " + ":lastName";
				}
				else
				{
					sQuery += "p.lastName LIKE " + ":lastName";
				}
			}
			if(objSearchData.getCity().isEmpty() == false)
			{
				bCity = true;
				if(bFN == true || bLN == true)
				{
					sQuery += " OR p.city LIKE " + ":city";
				}
				else
				{
					sQuery += "p.city LIKE " + ":city";
				}
			}
			if(objSearchData.getState().isEmpty() == false)
			{
				bState = true;
				if(bFN == true || bLN == true || bCity == true)
				{
					sQuery += " OR p.state LIKE " + ":state";
				}
				else
				{
					sQuery += "p.state LIKE " + ":state";
				}
			}
			return sQuery;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sQuery;
	}

}
