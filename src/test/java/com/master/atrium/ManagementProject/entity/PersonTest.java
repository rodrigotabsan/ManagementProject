package com.master.atrium.ManagementProject.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.entity.Task;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class PersonTest {
			
	@Test
	@Order(1)
	void getPersonTest() {
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		Set<Task> tasks = new HashSet<>();
		tasks.add(task);
		person.setTasks(tasks);
		person.setPasswordConfirmation("1234");
		assertNotNull(person);
		assertNotNull(person.getDni());
		assertNotNull(person.getName());
		assertNotNull(person.getLastname1());
		assertNotNull(person.getLastname2());
		assertNotNull(person.getUser());
		assertNotNull(person.getEmail());
		assertNotNull(person.getStartDate());
		assertNotNull(person.getEndDate());
		assertNotNull(person.getRole());
		assertNotNull(person.getPassword());
		assertNotNull(person.getPasswordConfirmation());
		assertNotNull(person.getTasks());
	}
	
	@Test
	@Order(2)
	void hashcodeTest() {
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		Person person2 = new Person("09052512S", "RODRIGOUSER2", "RODRIGO2", "TABLADO2", "SANCHEZ2", "test2junit@test.com", "1234", role, new Date(), new Date());
		
		Set<Person> set= new HashSet<Person>();
		set.add(person);
		set.add(person2);
		assertTrue(set.contains(person2));
	}
	
	@Test
	@Order(3)
	void equalsTest() {
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		
		assertTrue(person.equals(person));
	}
}
