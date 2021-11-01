package com.master.atrium.ManagementProject.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import com.master.atrium.managementproject.repository.ProjectPersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.service.TaskService;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class TaskServiceImplTest {

	@Autowired
	TaskService taskService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ProjectPersonRepository projectPersonRepository;
		
	@Test
	@Order(1)
	void saveWithoutUpdateTest() throws EmailExistsException, UserExistsException {
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER1", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), null);
		Project project = new Project("JUNITPROJECT1", "TESTING PROJECT", new Date(), new Date());
		Task task1 = new Task("TASKJUNIT1", "TESTING TASK", new Date(), new Date());
		Task task2 = new Task("TASKJUNIT2", "TESTING TASK", new Date(), new Date());
				
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER1");
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT1");
		
		task1.setPerson(person);
		task1.setProject(project);
		task2.setPerson(person);
		task2.setProject(project);
		
		Task afterSave1 = taskService.save(task1);
		Task afterSave2 = taskService.save(task2);
		assertEquals("TASKJUNIT1", afterSave1.getName());
		assertEquals("TASKJUNIT2", afterSave2.getName());
	}
	
	@Test
	@Order(2)
	void saveWithUpdateTest() throws EmailExistsException, UserExistsException {
		Task task =  taskService.findByName("TASKJUNIT2");
		task.setName("PEPITOSTASK");
		Task afterSave = taskService.save(task);
		assertEquals("PEPITOSTASK", afterSave.getName());
	}
	
	@Test
	@Order(3)
	void saveWithUpdateWithMorePersonsTest() throws EmailExistsException, UserExistsException {
		Task task =  taskService.findByName("PEPITOSTASK");
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER2", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), null);
		Project project = new Project("JUNITPROJECT2", "TESTING PROJECT", new Date(), new Date());
		
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER2");
		
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT2");
		
		task.setPerson(person);
		task.setProject(project);
		
		Task afterSave = taskService.save(task);
		assertEquals(afterSave.getPersonId(), person.getId());
		assertEquals(afterSave.getProjectId(), project.getId());
	}
	
	@Test
	@Order(4)
	void findAllTest() {
		List<Task> tasks = taskService.findAll();
		assertTrue(!tasks.isEmpty());
	}
	
	@Test
	@Order(5)
	void findByNameTest() {
		Task task = taskService.findByName("PEPITOSTASK");	
		assertEquals("TESTING TASK",task.getDescription());
	}
	
	@Test
	@Order(6)
	void findByIdTest() {
		Task task = taskService.findById(taskService.findByName("PEPITOSTASK").getId());		
		assertEquals("PEPITOSTASK", task.getName());
	}
	
	@Test
	@Order(7)
	void findTasksByProjectTest() {
		Task task = taskService.findByName("PEPITOSTASK");
		Project project = projectRepository.findById(task.getProjectId());
		List<Task> tasks = taskService.findTasksByProject(project);
		assertTrue(!tasks.isEmpty());
	}
	
	@Test
	@Order(8)
	void findTasksByPersonTest() {
		Task task = taskService.findByName("PEPITOSTASK");
		Person person = personRepository.findById(task.getPersonId());
		List<Task> tasks = taskService.findTasksByPerson(person);
		assertTrue(!tasks.isEmpty());
	}
	
	@Test
	@Order(9)
	void deleteTest() throws RecordReferencedInOtherTablesException {
		Task task = new Task("JUNITTASK3", "TESTING TASK", new Date(), new Date());
		Person person = personRepository.findByUser("RODRIGOUSER1");
		Project project = projectRepository.findByName("JUNITPROJECT1");
		task.setPerson(person);
		task.setPersonId(person.getId());
		task.setProject(project);
		task.setProjectId(project.getId());		
		Task afterSave = taskService.save(task);	
		taskService.delete(afterSave);
		afterSave = taskService.findByName("JUNITTASK3");
		assertNull(afterSave);
	}
	
	@Test
	@Order(10)
	void deleteAllFields() throws RecordReferencedInOtherTablesException {
		Project project = projectRepository.findByName("JUNITPROJECT2");
		Project project2 = projectRepository.findByName("JUNITPROJECT1");	
		List<Person> persons = projectPersonRepository.findAllPersonsByIdProject(project.getId());
		List<Person> persons2 = projectPersonRepository.findAllPersonsByIdProject(project2.getId());
		List<Person> allPersons = personRepository.findAll();
		List<Project> allProjects = projectRepository.findAll();
		List<Task> allTasks = taskService.findAll();
		int indexProjects = 2;
		
		allTasks.forEach(task -> { 			
			taskService.delete(task);
		});	
		
		persons.forEach(person -> projectPersonRepository.delete(project, person));
		persons2.forEach(person -> projectPersonRepository.delete(project2, person));		
		
		allPersons.forEach(person -> { 
			if(!"test@email.com".equals(person.getEmail()) && !"test2@email.com".equals(person.getEmail())) {
				personRepository.deleteById(person.getId());
			}
		});		
		
		
		while(indexProjects < allProjects.size()) {
			projectRepository.deleteById(allProjects.get(indexProjects).getId());
			indexProjects++;
		}
	}

}
