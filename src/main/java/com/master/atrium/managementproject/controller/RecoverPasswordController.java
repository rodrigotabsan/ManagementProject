package com.master.atrium.managementproject.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.master.atrium.managementproject.dto.UserDto;
import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.service.ResetPasswordService;

/**
 * Controlador para la pantalla de recuperación de contraseñas
 * @author Rodrigo
 *
 */
@Controller
@RequestMapping("/recoverpassword")
public class RecoverPasswordController {
	
	/**
	 * Inyección de {@link PersonRepository}
	 */
	@Autowired
	PersonRepository personRepository;
	
	/**
	 * Inyección de {@link ResetPasswordService}
	 */
	@Autowired
	ResetPasswordService resetPasswordService;
	
	
	/**
	 * Constructor de la clase
	 */
	public RecoverPasswordController() {
		super();
	}
	
	@GetMapping()
    public ModelAndView passwordforgotten(ModelMap model) {
		model.addAttribute("user", new UserDto());
        return new ModelAndView("recoverpassword", model);
    }

	/**
	 * Petición GET para enviar el correo.
	 * @param model
	 * @return
	 */
	@PostMapping(value="forgotpassword")
    public ModelAndView resetPassword(@Valid UserDto user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			return new ModelAndView("main");
		}
		Person personFound = personRepository.findByUser(user.getUser());
		if(Objects.nonNull(personFound)) {
			resetPasswordService.resetPassword(personFound);
		}
		
		model.addAttribute("person", personFound);
        return new ModelAndView("login", model);
    }
	
	
	
}
