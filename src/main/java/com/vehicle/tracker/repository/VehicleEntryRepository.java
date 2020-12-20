package com.vehicle.tracker.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.tracker.model.VehicleEntryDao;

@Repository
public interface VehicleEntryRepository extends MongoRepository<VehicleEntryDao, String>{

	Optional<VehicleEntryDao> findByVehicleNumber(String vehicleNumber);

}
