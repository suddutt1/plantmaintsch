package com.ca.maintainance.scheduler.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataUtil {

	private static final Gson _SERIALIZER = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
	
	public static String toJson(Object obj)
	{
		if(obj!=null)
		{
			return _SERIALIZER.toJson(obj);
		}
		else
		{
			return "{}";
		}
	}
}
