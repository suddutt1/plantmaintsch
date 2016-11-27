package com.ibm.app.web.frmwk.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ibm.app.web.frmwk.MobileFrameworkHelper;
import com.ibm.app.web.frmwk.bean.ActionConfigurations;

public class MobileFrameworkHelperTest {

	@Test
	public void test() {
		ActionConfigurations configs = MobileFrameworkHelper
				.loadActionConfigs(new String[] { "com.ibm.mytelco.action.LoginAction" });
		assertNotNull(configs);
		System.out.println(configs);
	}

}
