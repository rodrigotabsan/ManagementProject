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
import com.master.atrium.managementproject.repository.ProjectPersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class PersonServiceImplTest {

	@Autowired
	PersonService personService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ProjectPersonRepository projectPersonRepository;
	
	@Test
	@Order(1)
	void saveWithoutUpdateTest() throws EmailExistsException, UserExistsException {				
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER10", "RODRIGO", "TABLADO", "SANCHEZ", "test10junit@test.com", "1234", role, new Date(), null);		
		Person afterSave = personService.save(person);
		assertEquals("RODRIGOUSER10", afterSave.getUser());
	}
	
	@Test
	@Order(2)
	void saveWithoutUpdateWithProjectsTest() throws EmailExistsException, UserExistsException {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT");
		
		Integer[] projects = {project.getId().intValue()};
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "RODRIGOUSER20", "RODRIGO", "TABLADO", "SANCHEZ", "test20junit@test.com", "1234", role, new Date(), null);
		person.setProjects(projects);
		Person afterSave = personService.save(person);
		assertEquals("RODRIGOUSER20", afterSave.getUser());
	}
	
	@Test
	@Order(3)
	void saveWithUpdateTest() throws EmailExistsException, UserExistsException {
		Person person = personService.findByUser("RODRIGOUSER10");
		person.setUser("PEPITO");
		Person afterSave = personService.save(person);
		assertEquals("PEPITO", afterSave.getUser());
	}
	
	@Test
	@Order(4)
	void saveWithUpdateWithMoreProjectsTest() throws EmailExistsException, UserExistsException {
		Person person = personService.findByUser("PEPITO");
		Project project = new Project("JUNITPROJECT2", "TESTING PROJECT2", new Date(), new Date());
		Project project2 = new Project("JUNITPROJECT3", "TESTING PROJECT3", new Date(), new Date());
		
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT2");
		
		projectRepository.insert(project2);
		project2 = projectRepository.findByName("JUNITPROJECT3");
		
		Integer[] projects = {project.getId().intValue(), project2.getId().intValue()};
		person.setProjects(projects);
		Person afterSave = personService.save(person);
		assertEquals(2, afterSave.getProjectList().size());
	}
	
	@Test
	@Order(5)
	void saveWithUpdateWithLessProjectsTest() throws EmailExistsException, UserExistsException {
		Person person = personService.findByUser("PEPITO");
		Project project = new Project("JUNITPROJECT4", "TESTING PROJECT4", new Date(), new Date());
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT4");
		
		Integer[] projects = {project.getId().intValue()};
		person.setProjects(projects);
		Person afterSave = personService.save(person);
		assertEquals(1, afterSave.getProjectList().size());
	}
	
	@Test
	@Order(6)
	void saveWithUpdateWithALotOfProjectsTest() throws EmailExistsException, UserExistsException {
		Person person = personService.findByUser("PEPITO");
		List<Project> projectList = projectRepository.findAll();
		Integer[] projects = new Integer[projectList.size()-2];
		int indexProjects = 0;
		int indexProjectList = 2;
		
		while(indexProjectList < projectList.size()) {
			projects[indexProjects] = projectList.get(indexProjectList).getId().intValue();
			indexProjects++;
			indexProjectList++;
		}		
		person.setProjects(projects);
		Person afterSave = personService.save(person);
		assertTrue(afterSave.getProjectList().size() > 0);
	}
	
	@Test
	@Order(7)
	void findAllTest() {
		List<Person> persons = personService.findAll();
		assertTrue(!persons.isEmpty());
	}
	

	@Test
	@Order(8)
	void findAllProjectsByPersonTest() {
		Person person = personService.findByUser("PEPITO");
		List<Project> projects = personService.findAllProjectsByPerson(person);
		assertTrue(!projects.isEmpty());
	}
	
	@Test
	@Order(9)
	void findByEmailTest() {
		Person person = personService.findByUser("RODRIGOUSER20");		
		assertEquals("test20junit@test.com", person.getEmail());
	}
	
	@Test
	@Order(10)
	void findByUserTest() {
		Person person = personService.findByEmail("test20junit@test.com");		
		assertEquals("RODRIGOUSER20", person.getUser());
	}
	
	@Test
	@Order(11)
	void findByIdTest() {
		Person person = personService.findById(personService.findByEmail("test2@email.com").getId());		
		assertEquals("12345678Z", person.getDni());
	}
	
	@Test
	@Order(12)
	void deleteTest() throws RecordReferencedInOtherTablesException {
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Person person = new Person("09052516S", "USERTODELETE", "RODRIGO", "TABLADO", "SANCHEZ", "testjunit3@test.com", "1234", role, new Date(), null);	
		personService.delete(person);
		person = personService.findByUser("USERTODELETE");
		assertNull(person);
	}
	
	@Test
	@Order(13)
	void deleteAllFields() throws RecordReferencedInOtherTablesException {
		Person person = personService.findByUser("PEPITO");
		Person person2 = personService.findByUser("RODRIGOUSER20");
		List<Project> projects = personService.findAllProjectsByPerson(person);
		List<Project> projects2 = personService.findAllProjectsByPerson(person2);		
		List<Project> allProjects = projectRepository.findAll();
		List<Person> allPersons = personService.findAll();
		int indexPersons = 2;
		
		for(Project project : projects) {
			projectPersonRepository.delete(project, person);
		}
		for(Project project : projects2) {
			projectPersonRepository.delete(project, person2);
		}
		
		for(Project project : allProjects) {
			projectRepository.deleteById(project.getId());
		}
		
		while(indexPersons < allPersons.size()) {
			personService.delete(allPersons.get(indexPersons));
			indexPersons++;
		}
		assertTrue(!personService.findAll().isEmpty());
	}
}
