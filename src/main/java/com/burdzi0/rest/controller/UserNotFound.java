package com.burdzi0.rest.controller;

public class UserNotFound extends RuntimeException {
	UserNotFound() {
		super();
	}

	public UserNotFound(String message) {
		super(message);
	}

	public UserNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFound(Throwable cause) {
		super(cause);
	}

	protected UserNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
