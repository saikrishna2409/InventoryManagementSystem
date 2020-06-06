package com.product.exception;

public class DataNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String exception) {
		// TODO Auto-generated constructor stub
		super(exception);
	}
	
	public DataNotFoundException() {
		// TODO Auto-generated constructor stub
		super("No Product Found With Given Name");
	}
}
