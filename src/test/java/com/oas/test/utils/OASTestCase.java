package com.oas.test.utils;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.oas.common.utils.OASAplicationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContextTest.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
public class OASTestCase {

	@Autowired
	private OASAplicationConfiguration appConfig;
	
	protected static final Logger logger = Logger.getLogger(OASTestCase.class);
	
	public OASAplicationConfiguration getAppConfig() {
		return appConfig;
	}


	public void setAppConfig(OASAplicationConfiguration appConfig) {
		this.appConfig = appConfig;
	}


	@Test
	public void test() {
		Assert.assertEquals(true, true);
	}
}
