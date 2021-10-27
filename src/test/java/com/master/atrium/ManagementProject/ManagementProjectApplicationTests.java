package com.master.atrium.ManagementProject;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import com.master.atrium.managementproject.controller.LoginController;
import com.master.atrium.managementproject.controller.MainController;
import com.master.atrium.managementproject.service.MessageService;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.ProjectService;
import com.master.atrium.managementproject.service.RoleService;
import com.master.atrium.managementproject.service.TaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ManagementProjectApplicationTests {
	@Autowired
	LoginController controllerLogin;
	@Autowired
	MainController controllerMain;
	@Autowired
	TaskService taskService;
	@Autowired
	RoleService roleService;
	@Autowired
	ProjectService projectService;
	@Autowired
	PersonService personService;
	@Autowired
	MessageService messageService;
	@Autowired
	UserDetailsService userDetailService;
	@Test
	void contextLoads() {
		assertNotNull(controllerLogin);
		assertNotNull(controllerMain);
		assertNotNull(taskService);
		assertNotNull(roleService);
		assertNotNull(projectService);
		assertNotNull(personService);
		assertNotNull(messageService);
		assertNotNull(userDetailService);
	}

}
