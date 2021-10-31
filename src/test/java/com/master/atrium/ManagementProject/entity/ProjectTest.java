package com.master.atrium.ManagementProject.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.master.atrium.managementproject.entity.Project;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ProjectTest {
			
	@Test
	@Order(1)
	void setFieldsTest() {
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", new Date(), new Date());
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(2016, 8, 19);
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		project.setName("d");
		project.setDescription("a");
		project.setStartDate(date);
		project.setEndDate(date);
		assertNotNull(project);		
		assertNotNull(project.getName());
		assertNotNull(project.getDescription());
		assertNotNull(project.getStartDate());
		assertNotNull(project.getEndDate());
		assertEquals("d", project.getName());
		assertEquals("a", project.getDescription());
		assertEquals(date, project.getStartDate());
		assertEquals(date, project.getEndDate());
	}
	
	@Test
	@Order(2)
	void getFieldsTest() {
		Date dateNow = new Date();
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", dateNow, dateNow);
		assertNotNull(project);		
		assertNotNull(project.getName());
		assertNotNull(project.getDescription());
		assertNotNull(project.getStartDate());
		assertNotNull(project.getEndDate());
		assertEquals("JUNITPROJECT", project.getName());
		assertEquals("TESTING PROJECT", project.getDescription());
		assertEquals(dateNow, project.getStartDate());
		assertEquals(dateNow, project.getEndDate());
	}
	
	@Test
	@Order(3)
	void hashcodeTest() {
		Date dateNow = new Date();
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", dateNow, dateNow);
		Project project2 = new Project("JUNITPROJECT2", "TESTING PROJECT2", dateNow, dateNow);
		
		Set<Project> set= new HashSet<Project>();
		set.add(project);
		set.add(project2);
		assertTrue(set.contains(project));
	}
	
	@Test
	@Order(4)
	void equalsTest() {
		Date dateNow = new Date();
		Project project = new Project("JUNITPROJECT", "TESTING PROJECT", dateNow, dateNow);
		Project project2 = new Project("JUNITPROJECT", "TESTING PROJECT", dateNow, dateNow);
		
		assertTrue(project.equals(project2));
	}
}
