package com.master.atrium.managementproject.controller;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.ProjectService;
import com.master.atrium.managementproject.service.RoleService;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;
import com.master.atrium.managementproject.utility.Utility;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

@Controller
@RequestMapping("/person")
public class PersonController {
	private static final String PERSON = "person";
	private static final String PROJECTS = "projects";
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	RoleService roleService;
		
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@GetMapping
    public ModelAndView list(ModelMap model) {
        Iterable<Person> persons = this.personService.findAll();
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());        
        model.addAttribute("persons", persons);        
		model.addAttribute(PERSON, person);		
        return new ModelAndView("listperson", model);
    }

	@GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Person viewperson, ModelMap model) {
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
        
        Iterable<Project> projects = viewperson.getProjectList();
        model.addAttribute("viewperson", viewperson);        
		model.addAttribute(PERSON, person);
		model.addAttribute(PROJECTS, projects);
        return new ModelAndView("viewperson", model);
    }
	
	@GetMapping(value = "createform")
    public ModelAndView createForm(ModelMap model) {
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	Iterable<Role> roles = roleService.findAll();
    	Iterable<Project> projects = projectService.findAll();
    	Person createperson = new Person();
    	model.addAttribute(PERSON, person);
    	model.addAttribute("createperson", createperson);
    	model.addAttribute("roles", roles);
		model.addAttribute(PROJECTS, projects);
    	return new ModelAndView("registrationperson", model);
    }

    @PostMapping(value = "createperson")
    public ModelAndView create(@Valid Person createperson, BindingResult result, ModelMap model) throws EmailExistsException, UserExistsException {
        if (result.hasErrors()) {
            return new ModelAndView("registrationperson", "formErrors", result.getAllErrors());
        }         		
		personService.save(createperson);
        return new ModelAndView("redirect:/person/");
    }

    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) throws RecordReferencedInOtherTablesException {
    	Person person = personService.findById(id);
        this.personService.delete(person);
        return new ModelAndView("redirect:/person/");
    }
    
    @GetMapping(value = "viewmodify/{id}")
    public ModelAndView viewmodifyForm(@PathVariable("id") Person modifyperson, ModelMap model) {
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	Iterable<Role> roles = roleService.findAll();
    	Collection<Project> allprojects = Utility.convertIterableProjectToListProject(projectService.findAll());
    	modifyperson = personService.findById(modifyperson.getId());
    	Collection<Project> projectsselected = personService.findById(modifyperson.getId()).getProjectList();
    	    	
    	Set<Long> idsprojselected = projectsselected.stream()
    	        .map(Project::getId)
    	        .collect(Collectors.toSet());
    	Set<Project> projectsnotselected = allprojects.stream()
    	        .filter(proj -> !idsprojselected.contains(proj.getId()))
    	        .collect(Collectors.toSet());
    	
        model.addAttribute("modifyperson", modifyperson);        
		model.addAttribute(PERSON, person);
		model.addAttribute("roles", roles);
		model.addAttribute(PROJECTS, projectsnotselected);
		model.addAttribute("projectsselected", projectsselected);
        return new ModelAndView("formperson", model);
    }

    @PostMapping(value = "modify")
    public ModelAndView modifyForm(@ModelAttribute Person modifyperson, BindingResult result, ModelMap model) throws EmailExistsException, UserExistsException {
    	if (result.hasErrors()) {
            return new ModelAndView("formperson", "formErrors", result.getAllErrors());
        }
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());	   
		personService.save(modifyperson);
		model.addAttribute("modifyperson.id", modifyperson.getId());        
		model.addAttribute(PERSON, person);   	
        return new ModelAndView("redirect:/person/{modifyperson.id}", model);
    }

}
