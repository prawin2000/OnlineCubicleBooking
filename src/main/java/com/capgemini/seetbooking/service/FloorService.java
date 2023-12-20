package com.capgemini.seetbooking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.seetbooking.exception.DuplicateFloorNumberException;
import com.capgemini.seetbooking.exception.FloorNotFound;
import com.capgemini.seetbooking.exception.OfficeNotFoundException;
import com.capgemini.seetbooking.model.Floor;
import com.capgemini.seetbooking.repository.FloorRepository;
import com.capgemini.seetbooking.repository.OfficeRepository;

@Service
public class FloorService {
	@Autowired
	private FloorRepository floorRepository;
	@Autowired
	private OfficeRepository officeRepository;
	public String create(Floor floor) {
		boolean validateOffice = validateOffice(floor);
		if (validateOffice) {
			if (floor.getId() != null) {
				updateFloor(floor);
				return "Floor is Updated";
			} else {
				if (isFloorNumberExists(floor.getFloorNumber())) {
					throw new DuplicateFloorNumberException("Floor number already exists");
				}
				createFloor(floor);
				return "Floor is created";
			}
		}
		return null;
	}

	public boolean isFloorNumberExists(String floorNumber) {
		return floorRepository.existsByFloorNumber(floorNumber);
	}
	public Floor createFloor(Floor floor) {
		return floorRepository.save(floor);
	}
	public Floor updateFloor(Floor floor) {
		Optional<Floor> existingFloorOptional = floorRepository.findById(floor.getId());    
		if (existingFloorOptional.isPresent()) {
			Floor existingFloor = existingFloorOptional.get();
			if (!existingFloor.getFloorNumber().equals(floor.getFloorNumber())  
					&& isFloorNumberExists(floor.getFloorNumber())) {
				throw new DuplicateFloorNumberException("Floor number already exists");
			}

			existingFloor.setFloorNumber(floor.getFloorNumber());

			return floorRepository.save(existingFloor);
		} else {
			throw new FloorNotFound("Floor not found");
		}
	}
  
	public boolean validateFloor(Floor floor) {
		if (floor.getFloorNumber() == null || floor.getFloorNumber().trim().isEmpty()) {
			throw new IllegalArgumentException("Floor number cannot be null or empty");
		}
		boolean existsById = officeRepository.existsById(floor.getOffice().getId());
		if (existsById)
			return true;

		throw new OfficeNotFoundException("office does't exist");

	}

	public boolean validateOffice(Floor floor) { 
		boolean existsById = officeRepository.existsById(floor.getOffice().getId());
		if (existsById)
			return true;

		throw new OfficeNotFoundException("office does't exist");

	}

}