package com.master.atrium.managementproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;

@Controller
@RequestMapping("/main")
public class MainController {

	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	PersonService personService;
	
	@GetMapping
    public ModelAndView list(Model model) {
		Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
		model.addAttribute("person", person);
        return new ModelAndView("main", "person", person);
    }

}
