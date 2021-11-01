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
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectPersonRepository;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.service.ProjectService;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ProjectServiceImplTest {

	@Autowired
	ProjectService projectService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	ProjectPersonRepository projectPersonRepository;
	
	@Test
	@Order(1)
	void saveWithoutUpdateTest() throws EmailExistsException, UserExistsException {				
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		Project afterSave = projectService.save(project);
		assertEquals("JUNITPROJECT", afterSave.getName());
	}
	
	@Test
	@Order(2)
	void saveWithoutUpdateWithPersonsTest() throws EmailExistsException, UserExistsException {
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), null);
		Project project = new Project("JUNITPROJECT2", "TESTING PROJECT", new Date(), new Date());
		
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER");
		Integer[] persons = {person.getId().intValue()};
		
		project.setPersons(persons);
		Project afterSave = projectService.save(project);
		assertEquals("JUNITPROJECT2", afterSave.getName());
	}
	
	@Test
	@Order(3)
	void saveWithUpdateTest() throws EmailExistsException, UserExistsException {
		Project project = projectService.findByName("JUNITPROJECT");
		project.setName("PEPITOSPROJECT");
		Project afterSave = projectService.save(project);
		assertEquals("PEPITOSPROJECT", afterSave.getName());
	}
	
	@Test
	@Order(4)
	void saveWithUpdateWithMorePersonsTest() throws EmailExistsException, UserExistsException {
		Project project = projectService.findByName("PEPITOSPROJECT");
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER2", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), null);
		Person person2 = new Person("09052516S", "RODRIGOUSER3", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), null);
		
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER2");
			
		personRepository.insert(person2);
		person2 = personRepository.findByUser("RODRIGOUSER3");
		Integer[] persons = {person.getId().intValue(), person2.getId().intValue()};
		project.setPersons(persons);
		Project afterSave = projectService.save(project);
		assertEquals(2, afterSave.getPersonList().size());
	}
	
	@Test
	@Order(5)
	void saveWithUpdateWithLessPersonsTest() throws EmailExistsException, UserExistsException {
		Project project = projectService.findByName("PEPITOSPROJECT");
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER4", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit@test.com", "1234", role, new Date(), null);	
		personRepository.insert(person);
		person = personRepository.findByUser("RODRIGOUSER4");
		
		Integer[] persons = {person.getId().intValue()};
		project.setPersons(persons);
		Project afterSave = projectService.save(project);
		assertEquals(1, afterSave.getPersonList().size());
	}
	
	@Test
	@Order(6)
	void saveWithUpdateWithALotOfPersonsTest() throws EmailExistsException, UserExistsException {
		Project project = projectService.findByName("PEPITOSPROJECT");
		List<Person> personList = personRepository.findAll();
		Integer[] persons = new Integer[personList.size()-2];
		int indexPersonList = 2;
		int indexPersons = 0;
		
		while(indexPersonList < personList.size()) {
			persons[indexPersons] = personList.get(indexPersonList).getId().intValue();
			indexPersonList++;
			indexPersons++;
		}		
		project.setPersons(persons);
		Project afterSave = projectService.save(project);
		assertTrue(afterSave.getPersonList().size()>0);
	}
	
	@Test
	@Order(7)
	void findAllTest() {
		List<Project> projects = projectService.findAll();
		assertTrue(!projects.isEmpty());
	}
	

	@Test
	@Order(8)
	void findAllPersonsByProjectTest() {
		Project project = projectService.findByName("PEPITOSPROJECT");
		List<Person> persons = projectService.findAllPersonsByProject(project);
		assertTrue(!persons.isEmpty());
	}
	
	@Test
	@Order(9)
	void findByNameTest() {
		Project project = projectService.findByName("PEPITOSPROJECT");	
		assertEquals("TESTING PROJECT", project.getDescription());
	}
	
	@Test
	@Order(10)
	void findByIdTest() {
		Project project = projectService.findById(projectService.findByName("PEPITOSPROJECT").getId());		
		assertEquals("PEPITOSPROJECT", project.getName());
	}
	
	@Test
	@Order(11)
	void deleteTest() throws RecordReferencedInOtherTablesException {
		Project project = new Project("JUNITPROJECT3", "TESTING PROJECT", new Date(), new Date());
		Project afterSave = projectService.save(project);	
		projectService.delete(afterSave);
		afterSave = projectService.findByName("JUNITPROJECT3");
		assertNull(afterSave);
	}
	
	@Test
	@Order(12)
	void deleteAllFields() throws RecordReferencedInOtherTablesException {
		Project project = projectService.findByName("JUNITPROJECT2");
		Project project2 = projectService.findByName("PEPITOSPROJECT");	
		List<Person> persons = projectService.findAllPersonsByProject(project);
		List<Person> persons2 = projectService.findAllPersonsByProject(project2);
		List<Person> allPersons = personRepository.findAll();
		List<Project> allProjects = projectService.findAll();
		int indexProjects = 0;
		
		persons.forEach(person -> projectPersonRepository.delete(project, person));
		persons2.forEach(person -> projectPersonRepository.delete(project2, person));		
		
		allPersons.forEach(person -> { 
			if(!"test@email.com".equals(person.getEmail()) && !"test2@email.com".equals(person.getEmail())) {
				personRepository.deleteById(person.getId());
			}
		});
		
		while(indexProjects < allProjects.size()) {			
			projectService.delete(allProjects.get(indexProjects));
			indexProjects++;
		}
	}
}
