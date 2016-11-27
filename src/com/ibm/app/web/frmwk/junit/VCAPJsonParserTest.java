package com.ibm.app.web.frmwk.junit;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;

public class VCAPJsonParserTest {

	private static final StringBuffer strBuf = new StringBuffer();	
	@Before
	public void before()
	{
		strBuf.append("{" );
		strBuf.append("    \"mongolab\": [" );
		strBuf.append("      {" );
		strBuf.append("        \"credentials\": {" );
		strBuf.append("          \"uri\": \"mongodb://IbmCloud_shv47j4v_dacamm0v_iriv13ie:yHTjEpoAm_AFG-gN57tZfMrLynlzh2mT@ds027708.mongolab.com:27708/IbmCloud_shv47j4v_dacamm0v\"" );
		strBuf.append("        }," );
		strBuf.append("        \"label\": \"mongolab\"," );
		strBuf.append("        \"name\": \"MongoLab-bl\"," );
		strBuf.append("        \"plan\": \"sandbox\"," );
		strBuf.append("        \"tags\": [" );
		strBuf.append("          \"Data Stores\"," );
		strBuf.append("          \"Development Tools\"," );
		strBuf.append("          \"Infrastructure\"," );
		strBuf.append("          \"Service\"," );
		strBuf.append("          \"data_management\"," );
		strBuf.append("          \"ibm_third_party\"" );
		strBuf.append("        ] " );
		strBuf.append("      } " );
		strBuf.append("    ] " );
		strBuf.append("  }" );
	}
	@Test
	public void test() {
		System.out.println(strBuf);
		Gson gson  = new Gson();
		Object obj = gson.fromJson(strBuf.toString(), Object.class);
		Map<String,Object> map = ( Map<String,Object>) obj;
		List<Object> serviceDetails = (List<Object>)(map.get("mongolab"));
		if(serviceDetails!=null )
		{
			Map<String,Object> serviceAttrs= (Map<String,Object>)serviceDetails.get(0);
			Map<String,String> credentials =( Map<String,String>)serviceAttrs.get("credentials");
			System.out.println(credentials.get("uri"));
		}
		//System.out.println(serviceDetails.get("credentials"));
		
	}

}
