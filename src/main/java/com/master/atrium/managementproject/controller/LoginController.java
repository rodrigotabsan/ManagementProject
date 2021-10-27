package com.master.atrium.managementproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;

@Controller
public class LoginController {

	@Autowired
	PersonService personService;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@GetMapping("/")
    public String home() {		
        return "redirect:/main";
    }

}
