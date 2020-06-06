package com.productdetailsservice.exception;

public class DataNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	public DataNotFoundException() {
		// TODO Auto-generated constructor stub
		super("No Product With This Name Found");
	}
}
