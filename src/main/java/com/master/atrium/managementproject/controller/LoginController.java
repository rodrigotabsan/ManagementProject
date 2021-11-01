package com.master.atrium.managementproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;

/**
 * Controlador para el login
 * @author Rodrigo
 *
 */
@Controller
public class LoginController {

	/**
	 * Inyección de {@link PersonService}
	 */
	@Autowired
	PersonService personService;
	
	/**
	 * Inyección de {@link UserDetailsServiceImpl}
	 */
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	/**
	 * Redirige a la pantalla principal
	 * @return
	 */
	@GetMapping("/")
    public String home() {		
        return "redirect:/main";
    }
	
}
