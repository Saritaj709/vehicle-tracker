package com.vehicle.tracker.exception;

public class ValueAlreadyExistsException extends Exception{
	private static final long serialVersionUID = 1L;
	public ValueAlreadyExistsException(final String message){
		super(message);
	}
}
