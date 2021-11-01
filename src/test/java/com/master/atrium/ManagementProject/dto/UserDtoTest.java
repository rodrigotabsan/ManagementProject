package com.master.atrium.ManagementProject.dto;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.master.atrium.managementproject.dto.UserDto;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class UserDtoTest {
			
	@Test
	@Order(1)
	void gettersTaskMessageDtoTest() {
		String user = "rodrig";
		UserDto userDto = new UserDto(user);
		assertNotNull(userDto);
		assertNotNull(userDto.getUser());
	}
	
	@Test
	@Order(2)
	void settersTaskMessageDtoTest() {
		String user = "rodrig";
		UserDto userDto = new UserDto(user);
		assertNotNull(userDto);
		assertNotNull(userDto.getUser());
	}
	
	@Test
	@Order(3)
	void hashcodeTest() {
		UserDto userDto = new UserDto("SOMETHING");
		UserDto userDto2 = new UserDto("SOMETHING2");
		
		Set<UserDto> set= new HashSet<UserDto>();
		set.add(userDto);
		set.add(userDto2);
		assertTrue(set.contains(userDto2));
	}
	
	@Test
	@Order(4)
	void equalsTest() {
		UserDto userDto = new UserDto("SOMETHING");
		UserDto userDto2 = new UserDto("SOMETHING");
		
		assertTrue(userDto.equals(userDto2));
	}
}
