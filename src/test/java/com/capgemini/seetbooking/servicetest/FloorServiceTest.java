package com.capgemini.seetbooking.servicetest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.seetbooking.exception.OfficeNotFoundException;
import com.capgemini.seetbooking.model.Floor;
import com.capgemini.seetbooking.model.Office;
import com.capgemini.seetbooking.repository.FloorRepository;
import com.capgemini.seetbooking.repository.OfficeRepository;
import com.capgemini.seetbooking.service.FloorService;

@SpringBootTest   
public class FloorServiceTest {
	@Mock       
	private OfficeRepository officeRepository;

	@Mock
	private FloorRepository floorRepository;

@InjectMocks    
	private FloorService floorService;

	@Test       
	void testValidateOffice() {

		Floor floor = new Floor();
		floor.setId(1L);
		Office office = new Office();
		office.setId(1L);
		floor.setOffice(office);

		
		when(officeRepository.existsById(office.getId())).thenReturn(true);

		
		boolean result = floorService.validateOffice(floor);

		verify(officeRepository, times(1)).existsById(office.getId());

		
		assertTrue(result);
	}

	@Test
	void testValidateOfficeOfficeNotFoundException() {
		
		Floor floor = new Floor();
		floor.setId(1L);
		Office office = new Office();
		office.setId(1L);
		floor.setOffice(office);

		
		when(officeRepository.existsById(office.getId())).thenReturn(false);

		
		assertThrows(OfficeNotFoundException.class, () -> floorService.validateOffice(floor));

		
		verify(officeRepository, times(1)).existsById(office.getId());
	}

	@Test
	void testValidateFloor() {
		
		Floor floor = new Floor();
		floor.setId(1L);
		floor.setFloorNumber("1");
		Office office = new Office();
		office.setId(1L);
		floor.setOffice(office);

		when(officeRepository.existsById(office.getId())).thenReturn(true);

		
		boolean result = floorService.validateFloor(floor);

		
		verify(officeRepository, times(1)).existsById(office.getId());

		
		assertTrue(result);
	}

	@Test
	void testValidateFloorOfficeNotFoundException() {
		
		Floor floor = new Floor();
		floor.setId(1L);
		floor.setFloorNumber("1");
		Office office = new Office();
		office.setId(1L);
		floor.setOffice(office);

		
		when(officeRepository.existsById(office.getId())).thenReturn(false);

		
		assertThrows(OfficeNotFoundException.class, () -> floorService.validateFloor(floor));

		
		verify(officeRepository, times(1)).existsById(office.getId());
	}

	@Test
	void testValidateFloorIllegalArgumentException() {
		
		Floor floor = new Floor();
		floor.setId(1L);
		floor.setFloorNumber(null); // Setting floor number to null

		
		assertThrows(IllegalArgumentException.class, () -> floorService.validateFloor(floor));
	}

}     
