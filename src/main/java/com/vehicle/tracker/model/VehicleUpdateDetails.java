package com.vehicle.tracker.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleUpdateDetails {
	private String vehicleNumber;
	private Date updatedOn;
	private boolean fine;
}
