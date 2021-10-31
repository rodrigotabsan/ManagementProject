package com.master.atrium.managementproject.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;

/**
 * Controlador para la pantalla principal
 * @author Rodrigo
 *
 */
@Controller
@RequestMapping("/main")
public class MainController {

	/**
	 * Inyección de {@link UserDetailsServiceImpl}
	 */
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	/**
	 * Inyección de {@link PersonService}
	 */
	@Autowired
	PersonService personService;
	
	/**
	 * Petición GET para redirigir a la pantalla principal
	 * @param model
	 * @return
	 */
	@GetMapping
    public ModelAndView main(ModelMap model) {
		Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
		Date[] startDates =  personService.getArrayStartDates(person);
		Date[] endDates =  personService.getArrayEndDates(person);
		model.addAttribute("person", person);
		model.addAttribute("startDates", startDates);
		model.addAttribute("endDates", endDates);
        return new ModelAndView("main", model);
    }
	
	

}
