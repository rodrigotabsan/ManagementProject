package com.master.atrium.ManagementProject.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class RoleTest {
			
	@Test
	@Order(1)
	void getPersonsTest() {
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), null);
		List<Person> persons = new ArrayList<>();
		persons.add(person);
		role.setPersons(persons);
		
		assertNotNull(role.getPersons());
		assertTrue(!role.getPersons().isEmpty());
	}
	
	@Test
	@Order(2)
	void getNameTest() {
		Role role = new Role("SOMETHING");
				
		assertNotNull(role.getName());
		assertEquals("SOMETHING", role.getName());
	}
}
