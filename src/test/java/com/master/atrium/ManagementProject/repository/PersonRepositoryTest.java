package com.master.atrium.ManagementProject.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.RoleRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class PersonRepositoryTest {
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	RoleRepository roleRepository;
	
	Date date = new Date();
	
	@Test
	@Order(1)
	void insertPerson() {
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, date, null);
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER");		
		assertNotNull(person);
	}
	
	@Test
	@Order(2)
	void findAll() {
		List<Person> persons = personRepository.findAll();
	    assertTrue(persons.size() > 0);
	    for(Person person : persons) {
	    	assertNotNull(person.getUser());
	    	assertNotNull(person.getDni());
	    	assertNotNull(person.getName());
	    	assertNotNull(person.getLastname1());
	    	assertNotNull(person.getLastname2());
	    	assertNotNull(person.getEmail());
	    	assertNotNull(person.getStartDate());
	    }
	}
	  
	@Test
	@Order(3)
	void findByUser() {
		Person person = personRepository.findByUser("RODRIGOUSER");
	    assertNotNull(person);
	    assertEquals("RODRIGOUSER", person.getUser());
	    assertEquals("09052516S", person.getDni());
	    assertEquals("RODRIGO", person.getName());
	    assertEquals("TABLADO", person.getLastname1());
	    assertEquals("SANCHEZ", person.getLastname2());
	    assertEquals("testjunit@test.com", person.getEmail());
	    assertEquals(person.getStartDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
	}
	
	@Test
	@Order(4)
	void findByEmail() {
		Person person = personRepository.findByEmail("testjunit@test.com");
	    assertNotNull(person);
	}
	
	@Test
	@Order(5)
	void findById() {
		Person person = personRepository.findById(personRepository.findByEmail("test2@email.com").getId());
	    assertNotNull(person);
	}
	
	@Test
	@Order(6)
	void updatePerson() {
		Person person = personRepository.findByUser("RODRIGOUSER");
		person.setUser("RODRIGOUSER2");
		personRepository.update(person);
		person = personRepository.findByUser("RODRIGOUSER2");
	    assertNotNull(person);
	    assertEquals("RODRIGOUSER2", person.getUser());
	    assertEquals("09052516S", person.getDni());
	    assertEquals("RODRIGO", person.getName());
	    assertEquals("TABLADO", person.getLastname1());
	    assertEquals("SANCHEZ", person.getLastname2());
	    assertEquals("testjunit@test.com", person.getEmail());
	    assertEquals(person.getStartDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
	}
	
	@Test
	@Order(7)
	void deletePerson() {
		Person person = personRepository.findByUser("RODRIGOUSER2");
		personRepository.deleteById(person.getId());
		person = personRepository.findByUser("RODRIGOUSER2");
	    assertNull(person);	    
	}
}
