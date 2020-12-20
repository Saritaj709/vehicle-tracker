package com.vehicle.tracker.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "vehicles-with-violation")
public class VehicleWithViolationDao {
	@Id
	private String vehicleNumber;
	private String vehicleOwner;
	private boolean violationDone;
	private Date violationDate;
	private double fine;
	private String currentLocation;
}
