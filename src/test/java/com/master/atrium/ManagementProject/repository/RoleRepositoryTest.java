package com.master.atrium.ManagementProject.repository;

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
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.RoleRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class RoleRepositoryTest {
	
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PersonRepository personRepository;
	
	@Test
	@Order(1)
	void insertRoleTest() {
		Role role = new Role("ROLE_JUNIT");
		roleRepository.insert(role);
		role = roleRepository.findByName("ROLE_JUNIT");
		assertNotNull(role);
	}
	
	@Test
	@Order(2)
	void findAllTest() {
		List<Role> roles = roleRepository.findAll();
	    assertTrue(roles.size() > 0);
	}
	  
	@Test
	@Order(3)
	void findByNameTest() {
		Role role = roleRepository.findByName("ROLE_JUNIT");
	    assertNotNull(role);
	    assertEquals("ROLE_JUNIT", role.getName());
	}
	
	@Test
	@Order(4)
	void findByIdTest() {
		Role role = roleRepository.findById(1L);
	    assertNotNull(role);
	    assertEquals("ROLE_ADMIN", role.getName());
	}
	
	@Test
	@Order(5)
	void findPersonsByIdRoleTest() {
		Role role = roleRepository.findById(1L);
		List<Person> persons = roleRepository.findPersonsByIdRole(role.getId());
	    assertTrue(!persons.isEmpty());
	}
	
	@Test
	@Order(6)
	void updateRoleTest() {
		Role role = roleRepository.findByName("ROLE_JUNIT");
		role.setName("ROLE_JUNIT2");
		roleRepository.update(role);
		role = roleRepository.findByName("ROLE_JUNIT2");
		assertNotNull(role);
		assertEquals("ROLE_JUNIT2", role.getName());
	}
		
	@Test
	@Order(7)
	void deleteRole() {
		Role role = roleRepository.findByName("ROLE_JUNIT2");
		roleRepository.deleteById(role.getId());
		role = roleRepository.findByName("ROLE_JUNIT2");
		assertNull(role);	    
		if(!roleRepository.findAll().isEmpty()) {
			roleRepository.findAll().forEach(roleToDelete -> {
				if(!"ROLE_ADMIN".equals(roleToDelete.getName()) && !"ROLE_USER".equals(roleToDelete.getName())) {
					roleRepository.deleteById(roleToDelete.getId());
				}
			});
		}
	}
}
