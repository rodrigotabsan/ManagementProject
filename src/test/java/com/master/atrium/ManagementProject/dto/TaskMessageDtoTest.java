package com.master.atrium.ManagementProject.dto;

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

import com.master.atrium.managementproject.dto.TaskMessageDto;
import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.entity.Task;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class TaskMessageDtoTest {
			
	@Test
	@Order(1)
	void gettersTaskMessageDtoTest() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		
		Message message = new Message("COMMENT JUNIT", "Just a comment", new Date(), task);
		TaskMessageDto taskMessageDto = new TaskMessageDto(task, message);
		assertNotNull(taskMessageDto);
		assertNotNull(taskMessageDto.getTask());
		assertNotNull(taskMessageDto.getMessage());
		assertNotNull(taskMessageDto.getMessage().getBody());
		assertNotNull(taskMessageDto.getMessage().getSubject());
		assertNotNull(taskMessageDto.getTask().getName());
		assertNotNull(taskMessageDto.getTask().getDescription());
		assertNotNull(taskMessageDto.getTask().getEndDate());
		assertNotNull(taskMessageDto.getTask().getStartDate());
	}
	
	@Test
	@Order(2)
	void settersTaskMessageDtoTest() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		Role role = new Role("SOMETHING");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), new Date());
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		Task task2 = new Task("JUNIT TEST2", "This is a description2", new Date(), new Date(), person, project);
		
		Message message = new Message("COMMENT JUNIT", "Just a comment", new Date(), task);
		Message message2 = new Message("COMMENT JUNIT2", "Just a comment2", new Date(), task2);
		TaskMessageDto taskMessageDto = new TaskMessageDto(task, message);
		taskMessageDto.setTask(task2);
		taskMessageDto.setMessage(message2);		
		assertNotNull(taskMessageDto);
		assertNotNull(taskMessageDto.getTask());
		assertNotNull(taskMessageDto.getMessage());
		assertNotNull(taskMessageDto.getMessage().getBody());
		assertNotNull(taskMessageDto.getMessage().getSubject());
		assertNotNull(taskMessageDto.getTask().getName());
		assertNotNull(taskMessageDto.getTask().getDescription());
		assertNotNull(taskMessageDto.getTask().getEndDate());
		assertNotNull(taskMessageDto.getTask().getStartDate());
	}
	
	@Test
	@Order(3)
	void hashcodeTest() {
		Role role = new Role("SOMETHING");
		Role role2 = new Role("SOMETHING2");
		
		Set<Role> set= new HashSet<Role>();
		set.add(role);
		set.add(role2);
		assertTrue(set.contains(role2));
	}
	
	@Test
	@Order(4)
	void equalsTest() {
		Role role = new Role("SOMETHING");
		Role role2 = new Role("SOMETHING");
		
		assertTrue(role.equals(role2));
	}
}
