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

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.entity.Task;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectPersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.repository.TaskRepository;
import com.master.atrium.managementproject.service.MessageService;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class MessageServiceImplTest {

	@Autowired
	TaskRepository taskRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ProjectPersonRepository projectPersonRepository;
	@Autowired
	MessageService messageService;
		
	@Test
	@Order(1)
	void saveWithoutUpdateTest() throws EmailExistsException, UserExistsException {
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER1", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), null);
		Project project = new Project("JUNITPROJECT1", "TESTING PROJECT", new Date(), new Date());
		Task task1 = new Task("TASKJUNIT1", "TESTING TASK", new Date(), new Date());
		Task task2 = new Task("TASKJUNIT2", "TESTING TASK", new Date(), new Date());
		Message message = new Message("SUBJECTJUNIT", "TESTING MESSAGES", new Date());	
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER1");
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT1");
		
		task1.setPerson(person);
		task1.setProject(project);
		task2.setPerson(person);
		task2.setProject(project);
		
		taskRepository.insert(task1);
		taskRepository.insert(task2);
		task1 = taskRepository.findByName(task1.getName());
		task2 = taskRepository.findByName(task2.getName());
		message.setTask(task1);
		message.setTask(task2);
		message.setTaskId(task1.getId());
		message.setTaskId(task2.getId());
		Message afterSaveMessage = messageService.save(message);
		
		assertEquals("SUBJECTJUNIT", afterSaveMessage.getSubject());
	}
	
	@Test
	@Order(2)
	void saveWithUpdateTest() throws EmailExistsException, UserExistsException {
		Message message =  messageService.findBySubject("SUBJECTJUNIT");
		message.setBody("BODY HAS CHANGED");
		Message afterSave = messageService.save(message);
		assertEquals("BODY HAS CHANGED", afterSave.getBody());
	}
		
	@Test
	@Order(3)
	void findAllTest() {
		List<Message> messages = messageService.findAll();
		assertTrue(!messages.isEmpty());
	}
	
	@Test
	@Order(4)
	void findBySubjectTest() {
		Message message = messageService.findBySubject("SUBJECTJUNIT");	
		assertEquals("BODY HAS CHANGED", message.getBody());
	}
	
	@Test
	@Order(5)
	void findByIdTest() {
		Message message = messageService.findById(messageService.findBySubject("SUBJECTJUNIT").getId());		
		assertEquals("SUBJECTJUNIT", message.getSubject());
	}
		
	@Test
	@Order(6)
	void findMessagesByTaskTest() {
		Message message = messageService.findBySubject("SUBJECTJUNIT");
		Task task = taskRepository.findById(message.getTaskId());
		List<Message> messages = messageService.findMessagesByTask(task);
		assertTrue(!messages.isEmpty());
	}
	
	@Test
	@Order(7)
	void deleteTest() throws RecordReferencedInOtherTablesException {
		Task task = taskRepository.findByName("TASKJUNIT1");
		Message message = new Message("SUBJECTJUNITDELETED", "TESTING MESSAGES", new Date(), task);	
		message.setTaskId(task.getId());	
		Message afterSave = messageService.save(message);	
		messageService.delete(afterSave);
		afterSave = messageService.findBySubject("SUBJECTJUNITDELETED");
		assertNull(afterSave);
	}
	
	@Test
	@Order(8)
	void deleteAllFields() throws RecordReferencedInOtherTablesException {
		Project project = projectRepository.findByName("JUNITPROJECT1");	
		List<Person> persons = projectPersonRepository.findAllPersonsByIdProject(project.getId());
		List<Person> allPersons = personRepository.findAll();
		List<Project> allProjects = projectRepository.findAll();
		List<Task> allTasks = taskRepository.findAll();
		int indexProjects = 2;
		
		Message message = messageService.findBySubject("SUBJECTJUNIT");	
		messageService.delete(message);
		
		allTasks.forEach(task -> { 			
			taskRepository.deleteById(task.getId());
		});	
		
		persons.forEach(person -> projectPersonRepository.delete(project, person));	
		
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
