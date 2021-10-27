package com.master.atrium.ManagementProject.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.UserExistsException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class UserDetailsServiceImplTest {

	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
    PersonRepository personRepository;
	@Autowired
	RoleRepository roleRepository;
		
	@Test
	@Order(1)
	void loadUserByUsername() throws EmailExistsException, UserExistsException {		
		final Person personFound = personRepository.findByUser("rodrigo");
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(personFound.getUser());
		
		userDetails.getAuthorities().forEach(userDetailsAuthority -> assertEquals(userDetailsAuthority.getAuthority(), personFound.getRole().getName()));
		assertEquals("rodrigo", userDetails.getUsername());
	}

}
