package com.wirecard.common.utils;

import java.io.Serializable;

public class DateToolsException extends Exception implements Serializable {
	public DateToolsException() {
	}

	public DateToolsException(String msg) {
		super(msg);
	}

	public DateToolsException(Exception nestedException) {
		super(nestedException);
	}

	public DateToolsException(String msg, Exception nestedException) {
		super(msg, nestedException);
	}
}
