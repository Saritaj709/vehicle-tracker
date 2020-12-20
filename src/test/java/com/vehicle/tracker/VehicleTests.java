package com.vehicle.tracker;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;

import com.vehicle.tracker.exception.ValueAlreadyExistsException;
import com.vehicle.tracker.mail.MailServiceImpl;
import com.vehicle.tracker.model.MailDTO;
import com.vehicle.tracker.model.VehicleEntryDao;
import com.vehicle.tracker.model.VehicleUpdateDetails;
import com.vehicle.tracker.model.VehicleWithViolationDao;
import com.vehicle.tracker.repository.VehicleEntryRepository;
import com.vehicle.tracker.repository.VehiclesWithViolationRepository;
import com.vehicle.tracker.service.VehicleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class VehicleTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleTests.class);
	@InjectMocks
	private VehicleServiceImpl vehicleService;

	@Spy
	private VehicleEntryRepository entryRepository;

	@Spy
	private VehiclesWithViolationRepository vehiclesWithViolationsRepository;

	private static VehicleWithViolationDao violationDao = null;

	private static List<VehicleWithViolationDao> checkViolatedVehicleList = null;

	@InjectMocks
	private MailServiceImpl mailService;

	@Mock
	private JavaMailSender mailSender;

	private static List<VehicleEntryDao> registeredVehicles = null;
	private static VehicleEntryDao vehicleEntryDao = null;

	@Mock
	private MimeMessage message;

	private static MailDTO mail = null;

	private static List<String> violatedVehicleNumbers = new ArrayList<>();

	private static Optional<VehicleEntryDao> checkvehicleInDB = null;

	private static Optional<VehicleEntryDao> checkvehicleInDB1 = null;

	private static VehicleEntryDao vehicleEntryDao1 = null;

	private static VehicleUpdateDetails vehicleUpdateDetails = null;

	@BeforeAll
	static void setUp() {
		violationDao = new VehicleWithViolationDao();
		violationDao.setCurrentLocation("NH road");
		violationDao.setFine(100);
		violationDao.setVehicleNumber("VH01AE8001");
		violationDao.setVehicleOwner("Paul");
		violationDao.setViolationDone(true);
		violationDao.setViolationDate(new Date());

		checkViolatedVehicleList = new ArrayList<>();
		checkViolatedVehicleList.add(violationDao);

		vehicleEntryDao = new VehicleEntryDao();
		vehicleEntryDao.setMake("TOYOTA");
		vehicleEntryDao.setPurchasedOn(new Date());
		vehicleEntryDao.setVehicleColor("Black");
		vehicleEntryDao.setVehicleNumber("VH01AE8001");
		vehicleEntryDao.setVehicleOwner("Shiva");
		registeredVehicles = new ArrayList<>();
		registeredVehicles.add(vehicleEntryDao);

		violatedVehicleNumbers.add(violationDao.getVehicleNumber());

		mail = new MailDTO();
		mail.setTo("jaiswalsarita701@gmail.com");
		mail.setSubject("Alert, vehicle with vehicle number " + violatedVehicleNumbers.toString()
				+ "is having pending fine, person is nearby you!!");
		mail.setText("Catch Violated Vehicles!!");

		checkvehicleInDB = Optional.of(vehicleEntryDao);

		vehicleEntryDao1 = new VehicleEntryDao();
		vehicleEntryDao1.setVehicleNumber("0976");

		checkvehicleInDB1 = Optional.of(vehicleEntryDao1);

		vehicleUpdateDetails = new VehicleUpdateDetails();
		vehicleUpdateDetails.setVehicleNumber(vehicleEntryDao.getVehicleNumber());
		vehicleUpdateDetails.setFine(true);
	}

	@Test
	public void catchAllViolatedVehicles() throws MessagingException {
		Mockito.when(vehiclesWithViolationsRepository.findAllByCurrentLocation(violationDao.getCurrentLocation()))
				.thenReturn(checkViolatedVehicleList);
		ArgumentCaptor<MimeMessage> mimeMessageArgumentCaptor = ArgumentCaptor.forClass(MimeMessage.class);
		when(mailSender.createMimeMessage()).thenReturn(message);

		doNothing().when(mailSender).send(message);

		mailService.sendMail(mail);
		verify(mailSender, times(1)).send((mimeMessageArgumentCaptor.capture()));
		// assertEquals("subject", mimeMessageArgumentCaptor.getValue().getSubject());
		vehicleService.catchViolatedVehicles(violationDao.getCurrentLocation());
	}

	@Test
	public void findAllVehicles() {
		Mockito.when(entryRepository.findAll()).thenReturn(registeredVehicles);
		vehicleService.getAllVehicles();
	}

	@Test
	public void getVehicleByVehicleNumber() {
		Mockito.when(entryRepository.findByVehicleNumber(vehicleEntryDao.getVehicleNumber()))
				.thenReturn(checkvehicleInDB);
		vehicleService.getVehicleByVehicleNumber(vehicleEntryDao.getVehicleNumber());
	}

	@Test
	public void registerNewVehicle() {
		Mockito.when(entryRepository.findByVehicleNumber(vehicleEntryDao.getVehicleNumber()))
				.thenReturn(checkvehicleInDB);
		try {
			vehicleService.registerNewVehicle(vehicleEntryDao);
		} catch (ValueAlreadyExistsException e) {
			LOGGER.info(e.getMessage());
		}
	}

	@Test
	public void registerViolatedVehicle() {
		Mockito.when(vehiclesWithViolationsRepository.findByVehicleNumber(violationDao.getVehicleNumber()))
				.thenReturn(Optional.of(violationDao));
		try {
			vehicleService.registerViolatedVehicle(violationDao);
		} catch (ValueAlreadyExistsException e) {
			LOGGER.info(e.getMessage());
		}
	}

	@Test
	public void updateVehicleDetails() {
		Mockito.when(entryRepository.findByVehicleNumber(vehicleEntryDao.getVehicleNumber()))
				.thenReturn(checkvehicleInDB);
		try {
		vehicleService.updateVehicleDetails(vehicleUpdateDetails);
		}
		catch(Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	@Test
	public void removeViolationByVehicleNumber() {
		Mockito.when(vehiclesWithViolationsRepository
				.findByVehicleNumberAndViolationDone(violationDao.getVehicleNumber(), true))
				.thenReturn(Optional.of(violationDao));
		vehicleService.removeViolationByVehicleNumber(violationDao.getVehicleNumber());
	}
}
