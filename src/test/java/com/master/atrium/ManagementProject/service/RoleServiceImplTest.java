package com.master.atrium.ManagementProject.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.service.RoleService;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class RoleServiceImplTest {

	@Autowired
	RoleService roleService;
		
	@Test
	@Order(1)
	void saveWithoutUpdateTest() throws EmailExistsException, UserExistsException {
		Role role = roleService.save(new Role("ROLE_JUNIT"));
		
		assertEquals("ROLE_JUNIT", role.getName());
	}
	
	@Test
	@Order(2)
	void saveWithUpdateTest() throws EmailExistsException, UserExistsException {
		Role role = roleService.findByName("ROLE_JUNIT");
		role.setName("ROLE_JUNIT2");
		Role afterSave = roleService.save(role);
		assertEquals("ROLE_JUNIT2", afterSave.getName());
	}
		
	@Test
	@Order(4)
	void findAllTest() {
		List<Role> roles = roleService.findAll();
		assertTrue(!roles.isEmpty());
	}
	
	@Test
	@Order(5)
	void findByNameTest() {
		Role role = roleService.findByName("ROLE_JUNIT2");	
		assertNotNull(role);
	}
	
	@Test
	@Order(6)
	void findByIdTest() {
		Role role = roleService.findById(roleService.findByName("ROLE_JUNIT2").getId());	
		assertNotNull(role);
		assertEquals("ROLE_JUNIT2", role.getName());
	}
	
	@Test
	@Order(7)
	void findPersonsByRoleTest() {
		Role role = roleService.findByName("ROLE_ADMIN");		
		List<Person> persons = roleService.findPersonsByRole(role);
		assertTrue(!persons.isEmpty());
	}
	
	@Test
	@Order(8)
	void deleteTest() throws RecordReferencedInOtherTablesException {
		Role role = roleService.findByName("ROLE_JUNIT2");		
		roleService.delete(role);
		role = roleService.findByName("ROLE_JUNIT2");
		roleService.findAll().forEach(roleToDelete -> {
			if(!"ROLE_ADMIN".equals(roleToDelete.getName()) && !"ROLE_USER".equals(roleToDelete.getName())) {
				roleService.delete(roleToDelete);
			}
		});
		assertNull(role);
		
		
	}

}
