package com.swe645.assign5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZipInfo
{
	public static final Map<String, Map<String,String>> ZIP_MAP = new HashMap<String, Map<String, String>>();

	public static final List<String> ZIP_CODES = new ArrayList<String>();

	public static final ZipCode[] ZIPS = {new ZipCode("22312", new City("Alexandria", "VA")),
										  new ZipCode("22030", new City("Fairfax", "VA")),
										  new ZipCode("22301", new City("Tysons Corner", "MD")),
										  new ZipCode("20148", new City("Ashburn", "VA"))};


	/**
	 zip: 22312, city: Alexandria, state: VA
zip: 22030, city: Fairfax, state: VA
zip: 22301, city: Tysons Corner, state: MD
zip: 20148, city: Ashburn, state: VA
	 */
}

