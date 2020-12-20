package com.vehicle.tracker.exception;

public class VehicleFineNotPaidException extends Exception{
	private static final long serialVersionUID = 1L;
	public VehicleFineNotPaidException(final String message){
		super(message);
	}
}
