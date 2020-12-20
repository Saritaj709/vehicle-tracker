package com.vehicle.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vehicle.tracker.model.Response;

@ControllerAdvice
public class GlobalExceptionHandlerForVehicleTracker {
	@ExceptionHandler(VehicleFineNotPaidException.class)
	public ResponseEntity<Response> vehicleFineNotPaidExceptionHandler(VehicleFineNotPaidException e) {
		Response response = new Response();
		response.setMessage("Vehicle fine is not paid error, " + e.getMessage());
		response.setStatus(101);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoValueFoundException.class)
	public ResponseEntity<Response> noValueFoundExceptionHandler(NoValueFoundException e) {
		Response response = new Response();
		response.setMessage("No value found error, " + e.getMessage());
		response.setStatus(102);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ValueAlreadyExistsException.class)
	public ResponseEntity<Response> valueAlreadyExistsExceptionHandler(ValueAlreadyExistsException e) {
		Response response = new Response();
		response.setMessage("Vehicle already exists error, " + e.getMessage());
		response.setStatus(104);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}
