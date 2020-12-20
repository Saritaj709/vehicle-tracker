package com.vehicle.tracker.exception;

public class NoValueFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public NoValueFoundException(final String message){
		super(message);
	}
}
