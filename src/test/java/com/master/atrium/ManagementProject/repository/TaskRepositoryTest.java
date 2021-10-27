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

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.entity.Task;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.repository.TaskRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class TaskRepositoryTest {
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	RoleRepository roleRepository;
	
	Date date = new Date();
	
	@Test
	@Order(1)
	void insertTask() {
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, date, null);
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER");
		
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT");
		
		Task task = new Task("JUNIT TEST", "This is a description", new Date(), new Date(), person, project);
		taskRepository.insert(task);
		task = taskRepository.findByName("JUNIT TEST");		
		assertNotNull(task);
	}
	
	@Test
	@Order(2)
	void findAll() {
		List<Task> tasks = taskRepository.findAll();
	    assertTrue(tasks.size() > 0);
	    for(Task task : tasks) {
	    	assertNotNull(task.getName());
	    	assertNotNull(task.getDescription());
	    	assertNotNull(task.getStartDate());
	    	assertNotNull(task.getEndDate());
	    }
	}
	  
	@Test
	@Order(3)
	void findByName() {
		Task task = taskRepository.findByName("JUNIT TEST");
	    assertNotNull(task);
	    assertEquals("JUNIT TEST", task.getName());
	    assertEquals("This is a description", task.getDescription());
    	assertEquals(task.getStartDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
    	assertEquals(task.getEndDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
	}
		
	@Test
	@Order(4)
	void findById() {
		List<Task> tasks = taskRepository.findAll();
		Task task = taskRepository.findById(tasks.get(tasks.size()-1).getId());
	    assertNotNull(task);
	    assertEquals("JUNIT TEST", task.getName());
	    assertEquals("This is a description", task.getDescription());
    	assertEquals(task.getStartDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
    	assertEquals(task.getEndDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
	}
	@Test
	@Order(5)
	void findTasksByProjectId() {
		List<Task> allTasks = taskRepository.findAll();
		List<Task> tasksByProjectId = taskRepository.findTasksByProjectId(allTasks.get(allTasks.size()-1).getProjectId());
	    assertNotNull(tasksByProjectId);
	    for(Task task : tasksByProjectId) {
	    	assertNotNull(task.getName());
	    	assertNotNull(task.getDescription());
	    	assertNotNull(task.getStartDate());
	    	assertNotNull(task.getEndDate());
	    	Project project = projectRepository.findById(task.getProjectId());
	    	assertEquals("JUNITPROJECT", project.getName());
	    }
	}
	
	@Test
	@Order(6)
	void findTasksByPersonId() {
		List<Task> allTasks = taskRepository.findAll();
		List<Task> tasksByPersonId = taskRepository.findTasksByPersonId(allTasks.get(allTasks.size()-1).getPersonId());
	    assertNotNull(tasksByPersonId);
	    for(Task task : tasksByPersonId) {
	    	assertNotNull(task.getName());
	    	assertNotNull(task.getDescription());
	    	assertNotNull(task.getStartDate());
	    	assertNotNull(task.getEndDate());
	    	Person person = personRepository.findById(task.getPersonId());
	    	assertEquals("RODRIGOUSER", person.getUser());
	    }
	}
	
	@Test
	@Order(7)
	void updateTask() {
		Task task = taskRepository.findByName("JUNIT TEST");
		task.setName("JUNIT TEST2");
		taskRepository.update(task);
		task = taskRepository.findByName("JUNIT TEST2");
	    assertNotNull(task);
	    assertEquals("JUNIT TEST2", task.getName());
	    assertEquals("This is a description", task.getDescription());
    	assertEquals(task.getStartDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
    	assertEquals(task.getEndDate().toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate(), date.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDate());
	}
	
	@Test
	@Order(8)
	void deleteTask() {
		Task task = taskRepository.findByName("JUNIT TEST2");
		taskRepository.deleteById(task.getId());
		task = taskRepository.findById(task.getId());
		assertNull(task);
		
		Project project = projectRepository.findByName("JUNITPROJECT");
		projectRepository.deleteById(project.getId());
		
		Person person = personRepository.findByUser("RODRIGOUSER");
		personRepository.deleteById(person.getId());
		
	    	    
	}
}
