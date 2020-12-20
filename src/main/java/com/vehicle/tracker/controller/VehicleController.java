package com.vehicle.tracker.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.tracker.exception.ValueAlreadyExistsException;
import com.vehicle.tracker.model.Response;
import com.vehicle.tracker.model.VehicleEntryDao;
import com.vehicle.tracker.model.VehicleUpdateDetails;
import com.vehicle.tracker.model.VehicleWithViolationDao;
import com.vehicle.tracker.service.VehicleService;

@RestController
@RequestMapping("/api")
public class VehicleController {

	@Autowired
	private VehicleService service;

	/**
	 * 
	 * @return List of all vehicles
	 */
	@GetMapping(value = "/getAllVehicles")
	public ResponseEntity<List<VehicleEntryDao>> getAllVehicles() {

		return new ResponseEntity<>(service.getAllVehicles(), HttpStatus.OK);
	}
	
	@PostMapping(value="/catchViolatedVehicle")
	public ResponseEntity<Response> catchViolatedVehicles(@RequestParam(value="currentTrafficLocation")String currentTrafficLocation){
		
		return new ResponseEntity<>(service.catchViolatedVehicles(currentTrafficLocation), HttpStatus.OK);
	}

	/**
	 * 
	 * @return Single entity on basis of vehicleNumber
	 */
	@GetMapping(value = "/getVehicleByVehicleNumber")
	public ResponseEntity<Optional<VehicleEntryDao>> getVehicleByVehicleNumber(@RequestParam(value = "vehicleNumber") String vehicleNumber) {

		return new ResponseEntity<>(service.getVehicleByVehicleNumber(vehicleNumber), HttpStatus.OK);
	}

	/**
	 * 
	 * @return Single vehicle object posted
	 * @throws ValueAlreadyExistsException 
	 */
	@PostMapping(value = "/registerViolatedVehicle")
	public ResponseEntity<VehicleWithViolationDao> registerVioletVehicle(@RequestBody VehicleWithViolationDao violationVehicle) throws ValueAlreadyExistsException {

		return new ResponseEntity<>(service.registerViolatedVehicle(violationVehicle), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @return Single vehicle object posted
	 * @throws ValueAlreadyExistsException 
	 */
	@PostMapping(value = "/registerNewVehicle")
	public ResponseEntity<VehicleEntryDao> registerNewVehicle(@RequestBody VehicleEntryDao vehicle) throws ValueAlreadyExistsException {

		return new ResponseEntity<>(service.registerNewVehicle(vehicle), HttpStatus.OK);
	}

	/**
	 * 
	 * @return Single vehicle object updated
	 */
	@PutMapping(value = "/updateVehicle")
	public ResponseEntity<VehicleEntryDao> updateVehicleDetails(@RequestBody VehicleUpdateDetails vehicleUpdateDetails) {
		return new ResponseEntity<>(service.updateVehicleDetails(vehicleUpdateDetails), HttpStatus.OK);
	}

	/**
	 * 
	 * @return Single vehicle object deleted
	 */
	@DeleteMapping(value = "/deleteViolationByVehicleNumber")
	public ResponseEntity<?> removeViolationByVehicleNumber(@RequestParam(value = "vehicleNumber") String vehicleNumber) {

		return new ResponseEntity<>(service.removeViolationByVehicleNumber(vehicleNumber), HttpStatus.OK);
	}

}
