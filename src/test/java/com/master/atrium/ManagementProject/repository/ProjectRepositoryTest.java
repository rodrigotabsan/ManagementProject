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

import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.repository.ProjectRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class ProjectRepositoryTest {
	
	@Autowired
	ProjectRepository projectRepository;
	
	Date date = new Date();
	@Test
	@Order(1)
	void insertProject() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		projectRepository.insert(project);
		project = projectRepository.findByName("JUNITPROJECT");
		assertNotNull(project);
	}
	
	@Test
	@Order(2)
	void findAll() {
		List<Project> projects = projectRepository.findAll();
	    assertTrue(projects.size() > 0);
	    for(Project project : projects) {
	    	assertNotNull(project.getName());
	    	assertNotNull(project.getDescription());
	    	assertNotNull(project.getStartDate());
	    	assertNotNull(project.getEndDate());
	    }
	}
	
	@Test
	@Order(3)
	void findByName() {
		Project project = projectRepository.findByName("JUNITPROJECT");
	    assertNotNull(project);
	}
	
	@Test
	@Order(4)
	void findById() {
		List<Project> projects = projectRepository.findAll();
		Project project = projectRepository.findById(projects.get(projects.size()-1).getId());
	    assertNotNull(project);
	}
	
	@Test
	@Order(5)
	void updateProject() {
		Project project = projectRepository.findByName("JUNITPROJECT");
		project.setDescription("ROW UPDATED");
		projectRepository.update(project);
		project = projectRepository.findByName("JUNITPROJECT");
	    assertNotNull(project);
	}
	
	@Test
	@Order(6)
	void deleteProject() {
		Project project = projectRepository.findByName("JUNITPROJECT");
		projectRepository.deleteById(project.getId());
		project = projectRepository.findByName("JUNITPROJECT");
	    assertNull(project);	    
	}
}
