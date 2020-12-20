package com.vehicle.tracker.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "vehicles-registered")
public class VehicleEntryDao {
	 
	@Id
	private String vehicleNumber;
	private String vehicleOwner;
	private Date purchasedOn;
	private String vehicleColor;
	private String make;
}
