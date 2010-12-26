package com.oas.web;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.HeaderContributor;

public class JQueryHelper {

	public static void initJQuery(Component c) {
		c.add(HeaderContributor.forJavaScript(JQueryHelper.class,
				"jquery.pack.js"));
	}

}
