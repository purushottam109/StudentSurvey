package com.swe645.assign5;

import java.util.LinkedHashMap;
import java.util.Map;

public class ZipCode
{
	private final String zipCode;
	private final Map<String, String> cityMap = new LinkedHashMap<String, String>();

	public ZipCode(String zipCode, City...cities)
	{
		this.zipCode = zipCode;
		for(City c: cities)
		{
			cityMap.put(c.getName(), c.getState());
		}
		//cityMap.put(city.getName(), city.getState());
		ZipInfo.ZIP_CODES.add(zipCode);
		ZipInfo.ZIP_MAP.put(zipCode, cityMap);
	}

	public String getZipCode()
	{
		return zipCode;
	}

	public Map<String, String> getCityMap()
	{
		return cityMap;
	}
}
