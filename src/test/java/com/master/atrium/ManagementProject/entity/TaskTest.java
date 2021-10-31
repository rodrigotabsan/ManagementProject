package com.master.atrium.ManagementProject.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.entity.Task;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class TaskTest {
			
	@Test
	@Order(1)
	void getPersonsTest() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		Message message1 = new Message("COMMENT JUNIT1", "Just a comment1", new Date(), task);
		Message message2 = new Message("COMMENT JUNIT2", "Just a comment2", new Date(), task);
		List<Message> messages = new ArrayList<>();
		messages.add(message1);
		messages.add(message2);
		task.setMessages(messages);
		assertNotNull(task.getDescription());
		assertNotNull(task.getPerson());
		assertNotNull(task.getProject());
		assertNotNull(task.getEndDate());
		assertNotNull(task.getStartDate());
		assertNotNull(task.getName());
		assertNotNull(task.getMessages());
	}
		
	@Test
	@Order(2)
	void hashcodeTest() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		Task task2 = new Task("JUNIT TEST2", "This is a description2", new Date(), new Date(), person, project);
		Set<Task> set= new HashSet<Task>();
		set.add(task);
		set.add(task2);
		assertTrue(set.contains(task2));
	}
	
	@Test
	@Order(3)
	void equalsTest() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		Task task2 = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		
		assertTrue(task.equals(task2));
	}
}
