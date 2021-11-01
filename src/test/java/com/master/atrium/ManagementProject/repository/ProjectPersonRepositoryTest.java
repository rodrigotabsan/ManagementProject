package com.master.atrium.ManagementProject.repository;

import static org.junit.Assert.assertNotNull;
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
import org.springframework.test.context.ActiveProfiles;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.ProjectPerson;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectPersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;
import com.master.atrium.managementproject.repository.RoleRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class ProjectPersonRepositoryTest {
	
	@Autowired
	ProjectPersonRepository projectPersonRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	RoleRepository roleRepository;
	
	Date date = new Date();
	@Test
	@Order(1)
	void insertProject() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT");
		
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, date, null);
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER");
		
		projectPersonRepository.insert(project, person);
		
		ProjectPerson projectPerson = projectPersonRepository.findProjectPersonByIdPersonAndByIdProject(person.getId(), project.getId());
		assertNotNull(projectPerson);
	}
		
	@Test
	@Order(2)
	void findProjectPersonByIdPersonAndByIdProject() {
		Project project = projectRepository.findByName("JUNITPROJECT");
		Person person = personRepository.findByUser("RODRIGOUSER");
		ProjectPerson projectPerson = projectPersonRepository.findProjectPersonByIdPersonAndByIdProject(person.getId(), project.getId());
	    assertNotNull(projectPerson);
	    assertNotNull(projectPerson.getProjectId());
	    assertNotNull(projectPerson.getPersonId());
	}
	
	@Test
	@Order(3)
	void findAllProjectsByIdPerson() {
		Person person = personRepository.findByUser("RODRIGOUSER");
		List<Project> projects = projectPersonRepository.findAllProjectsByIdPerson(person.getId());
		assertTrue(projects.size() > 0);
		for(Project project : projects) {
		  	assertNotNull(project.getName());
		   	assertNotNull(project.getDescription());
		   	assertNotNull(project.getStartDate());
		   	assertNotNull(project.getEndDate());
		}
	}
	
	@Test
	@Order(4)
	void findAllPersonsByIdProject() {
		Project project = projectRepository.findByName("JUNITPROJECT");
		List<Person> persons = projectPersonRepository.findAllPersonsByIdProject(project.getId());
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
	@Order(5)
	void update() {
		Project projectBeforeUpdate = projectRepository.findByName("JUNITPROJECT");
		Project project = new Project("JUNITPROJECT2", "TESTING PROJECT", new Date(), new Date());
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT2");
		
		
		Person personBeforeUpdate = personRepository.findByUser("RODRIGOUSER");
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER2", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, date, null);
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER2");
		
		projectPersonRepository.update(project, person, projectBeforeUpdate, personBeforeUpdate);
		
		ProjectPerson projectPerson = projectPersonRepository.findProjectPersonByIdPersonAndByIdProject(person.getId(), project.getId());
		ProjectPerson projectPersonBeforeUpdate = projectPersonRepository.findProjectPersonByIdPersonAndByIdProject(personBeforeUpdate.getId(), projectBeforeUpdate.getId());
		assertNotNull(projectPerson);
	    assertNotNull(projectPerson.getProjectId());
	    assertNotNull(projectPerson.getPersonId());
	    assertNull(projectPersonBeforeUpdate);	    
	}
	
	@Test
	@Order(6)
	void deleteProject() {
		Project project = projectRepository.findByName("JUNITPROJECT2");
		Person person = personRepository.findByUser("RODRIGOUSER2");
		projectPersonRepository.delete(project, person);
		ProjectPerson projectPerson = projectPersonRepository.findProjectPersonByIdPersonAndByIdProject(person.getId(), project.getId());
		assertNull(projectPerson);	    
		
		personRepository.deleteById(person.getId());
		person = personRepository.findByUser("RODRIGOUSER");
		personRepository.deleteById(person.getId());
		
		projectRepository.deleteById(project.getId());
		project = projectRepository.findByName("JUNITPROJECT");
		projectRepository.deleteById(project.getId());
	}
}
