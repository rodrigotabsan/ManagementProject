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

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.entity.Task;
import com.master.atrium.managementproject.repository.MessageRepository;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.repository.TaskRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class MessageRepositoryTest {
	
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	RoleRepository roleRepository;
	
	Date date = new Date();
	
	@Test
	@Order(1)
	void insertMessage() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT");
		
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, date, null);
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER");
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		taskRepository.insert(task);
		task = taskRepository.findByName("JUNIT TEST");
		
		Message message = new Message("COMMENT JUNIT", "Just a comment", new Date(), task);
		messageRepository.insert(message);
		message = messageRepository.findBySubject("COMMENT JUNIT");		
		assertNotNull(message);
		assertNotNull(message.getBody());
    	assertNotNull(message.getDate());
	}
	
	@Test
	@Order(2)
	void findAll() {
		List<Message> messages = messageRepository.findAll();
		assertNotNull(messages);
	    assertTrue(!messages.isEmpty());
	    for(Message message : messages) {
	    	assertNotNull(message.getSubject());
	    	assertNotNull(message.getBody());
	    	assertNotNull(message.getDate());
	    }
	}
	  
	@Test
	@Order(3)
	void findBySubject() {
		Message message = messageRepository.findBySubject("COMMENT JUNIT");
	    assertNotNull(message);
	    assertEquals("COMMENT JUNIT", message.getSubject());
	    assertEquals("Just a comment", message.getBody());
	    assertEquals(message.getDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
	}
	
	@Test
	@Order(4)
	void findById() {
		List<Message> messages = messageRepository.findAll();
		Message message = messageRepository.findById(messages.get(messages.size()-1).getId());
	    assertNotNull(message);
	    assertEquals("COMMENT JUNIT", message.getSubject());
	    assertEquals("Just a comment", message.getBody());
	    assertEquals(message.getDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
	}
	
	@Test
	@Order(5)
	void findMessagesByTaskId() {
		Task task = taskRepository.findByName("JUNIT TEST");
		List<Message> messages = messageRepository.findMessagesByTaskId(task.getId());
	    assertNotNull(messages);
	    assertTrue(!messages.isEmpty());
	    for(Message message : messages) {
	    	assertNotNull(message.getSubject());
	    	assertNotNull(message.getBody());
	    	assertNotNull(message.getDate());
	    	assertNotNull(message.getTaskId());
	    }
	}
	
	@Test
	@Order(6)
	void updateMessage() {
		Message message = messageRepository.findBySubject("COMMENT JUNIT");
		message.setSubject("COMMENT JUNIT2");
		messageRepository.update(message);
		message = messageRepository.findBySubject("COMMENT JUNIT2");
	    assertNotNull(message);
	    assertEquals("COMMENT JUNIT2", message.getSubject());
	    assertEquals("Just a comment", message.getBody());
	    assertEquals(message.getDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
	}
	
	@Test
	@Order(7)
	void deleteMessage() {
		Message message = messageRepository.findBySubject("COMMENT JUNIT2");
		messageRepository.deleteById(message.getId());
		message = messageRepository.findBySubject("COMMENT JUNIT2");
	    assertNull(message);	    
	    
	    Task task = taskRepository.findByName("JUNIT TEST");
		taskRepository.deleteById(task.getId());
		task = taskRepository.findById(task.getId());
		assertNull(task);
		
		Project project = projectRepository.findByName("JUNITPROJECT");
		projectRepository.deleteById(project.getId());
		
		Person person = personRepository.findByUser("RODRIGOUSER");
		personRepository.deleteById(person.getId());
	}
}
