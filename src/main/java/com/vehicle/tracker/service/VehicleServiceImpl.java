package com.vehicle.tracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.tracker.exception.NoValueFoundException;
import com.vehicle.tracker.exception.ValueAlreadyExistsException;
import com.vehicle.tracker.mail.MailService;
import com.vehicle.tracker.model.MailDTO;
import com.vehicle.tracker.model.Response;
import com.vehicle.tracker.model.VehicleEntryDao;
import com.vehicle.tracker.model.VehicleTrackerEnum;
import com.vehicle.tracker.model.VehicleUpdateDetails;
import com.vehicle.tracker.model.VehicleWithViolationDao;
import com.vehicle.tracker.repository.VehicleEntryRepository;
import com.vehicle.tracker.repository.VehiclesWithViolationRepository;
import org.springframework.beans.factory.annotation.Value;

@Service
public class VehicleServiceImpl implements VehicleService{

	@Autowired
	private VehicleEntryRepository vehicleEntryRepository;
	
	@Autowired
	private VehiclesWithViolationRepository vehiclesWithViolationRepository;
	
	@Value("${spring.mail.username}")
	private String email;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public List<VehicleEntryDao> getAllVehicles() {
		return vehicleEntryRepository.findAll();
	}
	
	@Override
	public Response catchViolatedVehicles(String currentTrafficLocation) {
		List<VehicleWithViolationDao> checkViolatedVehiclesInDB=vehiclesWithViolationRepository.findAllByCurrentLocation(currentTrafficLocation);
		if(checkViolatedVehiclesInDB.size()==0) {
			throw new NoValueFoundException("No violated vehicles details found at current location");
		}
		
		List<String> violatedVehicleNumbers=new ArrayList<>();
		for(VehicleWithViolationDao violatedVehicle:checkViolatedVehiclesInDB) {
			if(!violatedVehicle.isViolationDone()) {
				throw new NoValueFoundException("No violations details found with given vehicle number");
			}
			violatedVehicleNumbers.add(violatedVehicle.getVehicleNumber());
		}
		
		Response response=new Response();
		
		response.setMessage("Alert, vehicle with vehicle number "+violatedVehicleNumbers.toString()+"is having pending fine, person is nearby you!!");
		response.setStatus(200);
		
		MailDTO mail = new MailDTO();
		mail.setTo(email);
		mail.setSubject(response.getMessage());
		mail.setText("Catch Violated Vehicles!!");
		mailService.sendMail(mail);
		return response;
	}

	@Override
	public Optional<VehicleEntryDao> getVehicleByVehicleNumber(String vehicleNumber) {
		Optional<VehicleEntryDao> checkvehicleInDB=vehicleEntryRepository.findByVehicleNumber(vehicleNumber);
		if(!checkvehicleInDB.isPresent()) {
			throw new NoValueFoundException("No details found with given vehicle number");
		}
		return vehicleEntryRepository.findByVehicleNumber(vehicleNumber);
	}
	
	@Override
	public VehicleEntryDao registerNewVehicle(VehicleEntryDao vehicle) throws ValueAlreadyExistsException {
		Optional<VehicleEntryDao> checkvehicleInDB=vehicleEntryRepository.findByVehicleNumber(vehicle.getVehicleNumber());
		if(checkvehicleInDB.isPresent()) {
			throw new ValueAlreadyExistsException("Vehicle with given vehicle number is altready present, enter a different vehicle number and try again.");
		}
		return vehicleEntryRepository.save(vehicle);
	}

	@Override
	public VehicleWithViolationDao registerViolatedVehicle(VehicleWithViolationDao violationVehicle) throws ValueAlreadyExistsException {
		Optional<VehicleWithViolationDao> checkvehicleInDB=vehiclesWithViolationRepository.findByVehicleNumber(violationVehicle.getVehicleNumber());
		if(checkvehicleInDB.isPresent()) {
			throw new ValueAlreadyExistsException("Vehicle with given vehicle number is altready present, enter a different vehicle number and try again.");
		}
		return vehiclesWithViolationRepository.save(violationVehicle);
	}

	@Override
	public VehicleEntryDao updateVehicleDetails(VehicleUpdateDetails vehicleUpdateDetails) {
		Optional<VehicleEntryDao> updatedDetails=vehicleEntryRepository.findByVehicleNumber(vehicleUpdateDetails.getVehicleNumber());
		if(!updatedDetails.isPresent()) {
			throw new NoValueFoundException("No details found with given vehicle number");
		}
		return vehicleEntryRepository.save(updatedDetails.get());
	}

	@Override
	public String removeViolationByVehicleNumber(String vehicleNumber) {
		Optional<VehicleWithViolationDao> violatedDao=vehiclesWithViolationRepository.findByVehicleNumberAndViolationDone(vehicleNumber,VehicleTrackerEnum.IS_FINE_PENDING.isFinePending());
		if(!violatedDao.isPresent()) {
			throw new NoValueFoundException("No details found with given vehicle number");
		}
		VehicleEntryDao originalDao=new VehicleEntryDao();
		originalDao.setVehicleNumber(violatedDao.get().getVehicleNumber());
		originalDao.setVehicleOwner(violatedDao.get().getVehicleOwner());
		vehicleEntryRepository.save(originalDao);
		vehiclesWithViolationRepository.delete(violatedDao.get());
		return "Violations solved for "+vehicleNumber+" ,it is now moved to database entries which does not contain any violations";
	}

}
