package com.vehicle.tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.tracker.model.VehicleWithViolationDao;

@Repository
public interface VehiclesWithViolationRepository extends MongoRepository<VehicleWithViolationDao,String>{

	Optional<VehicleWithViolationDao> findByVehicleNumber(String vehicleNumber);

	Optional<VehicleWithViolationDao> findByVehicleNumberAndViolationDone(String vehicleNumber, boolean violationDone);

	List<VehicleWithViolationDao> findAllByCurrentLocation(String currentTrafficLocation);

}
