package com.vehicle.tracker.service;

import java.util.List;
import java.util.Optional;

import com.vehicle.tracker.exception.ValueAlreadyExistsException;
import com.vehicle.tracker.model.Response;
import com.vehicle.tracker.model.VehicleEntryDao;
import com.vehicle.tracker.model.VehicleUpdateDetails;
import com.vehicle.tracker.model.VehicleWithViolationDao;

public interface VehicleService {

	List<VehicleEntryDao> getAllVehicles();

	Optional<VehicleEntryDao> getVehicleByVehicleNumber(String vehicleNumber);

	VehicleEntryDao registerNewVehicle(VehicleEntryDao vehicle) throws ValueAlreadyExistsException;

	VehicleEntryDao updateVehicleDetails(VehicleUpdateDetails vehicleUpdateDetails);

	String removeViolationByVehicleNumber(String vehicleNumber);

	Response catchViolatedVehicles(String currentTrafficLocation);

	VehicleWithViolationDao registerViolatedVehicle(VehicleWithViolationDao violationVehicle) throws ValueAlreadyExistsException;

}
