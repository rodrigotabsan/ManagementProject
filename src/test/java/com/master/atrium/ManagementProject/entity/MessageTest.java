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
import org.springframework.test.context.ActiveProfiles;

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.entity.Task;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class MessageTest {
			
	@Test
	@Order(1)
	void getMessageTest() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		
		Message message = new Message("COMMENT JUNIT", "Just a comment", new Date(), task);
		
		assertNotNull(message);
		assertNotNull(message.getBody());
		assertNotNull(message.getSubject());
		assertNotNull(message.getDate());
		assertNotNull(message.getTask());
		
	}
	
	@Test
	@Order(2)
	void hashcodeTest() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		
		Message message = new Message("COMMENT JUNIT", "Just a comment", new Date(), task);
		Message message2 = new Message("COMMENT JUNIT2", "Just a comment2", new Date(), task);
		
		Set<Message> set= new HashSet<Message>();
		set.add(message);
		set.add(message2);
		assertTrue(set.contains(message2));
	}
	
	@Test
	@Order(3)
	void equalsTest() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		
		Message message = new Message("COMMENT JUNIT", "Just a comment", new Date(), task);
		Message message2 = new Message("COMMENT JUNIT", "Just a comment", new Date(), task);
		
		assertTrue(message.equals(message2));
	}
}
