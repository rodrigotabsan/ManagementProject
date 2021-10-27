package com.master.atrium.managementproject.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.ProjectService;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;

@Controller
@RequestMapping("/project")
public class ProjectController {

	private static final String PERSON = "person";
	private static final String PERSONS = "persons";
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	ProjectService projectService;
	
	/*@Autowired
	ProjectRegistrationService projectRegistrationService;
	*/
	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
	}
	
	@GetMapping
    public ModelAndView list(ModelMap model) {        
        Iterable<Project> projects = this.projectService.findAll();
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
        model.addAttribute("projects", projects);        
		model.addAttribute(PERSON, person);
        return new ModelAndView("listproject", model);
    }

	@GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Project viewproject, ModelMap model) {
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
        //List<ProjectRegistration> projectRegistrations = projectRegistrationService.findByProject(viewproject);
        //Iterable<Person> persons = personService.findPersonsByProjectId(projectRegistrations);        
        model.addAttribute("viewproject", viewproject);        
		model.addAttribute(PERSON, person);
		//model.addAttribute(PERSONS, persons);
        return new ModelAndView("viewproject", model);
    }

    @GetMapping(value = "createform")
    public ModelAndView createForm(@Valid Project project, BindingResult result, ModelMap model) {
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	Iterable<Person> persons = personService.findAll();
    	Project createproject = new Project();
    	model.addAttribute(PERSON, person);
    	model.addAttribute("createproject", createproject);
		model.addAttribute(PERSONS, persons);
    	return new ModelAndView("registrationproject", model);
    }
    
    @PostMapping(value = "createproject")
    public ModelAndView create(@Valid Project createproject, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return new ModelAndView("registrationproject", "formErrors", result.getAllErrors());
        }
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());               
		
		Project projectcreated = projectService.save(createproject);
		//projectRegistrationService.saveProjectRegistrations(Arrays.asList(createproject.getPersons()), projectcreated);		
		
		model.addAttribute("projectcreated.id", projectcreated.getId()); 
		model.addAttribute(PERSON, person);
        model.addAttribute("globalMessage", "Successfully created a new project");
        return new ModelAndView("redirect:/project/{projectcreated.id}", model);
    }

    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) throws RecordReferencedInOtherTablesException {
    	Project project = projectService.findById(id);
        this.projectService.delete(project);
        return new ModelAndView("redirect:/project/");
    }

    @GetMapping(value = "viewmodify/{id}")
    public ModelAndView viewmodifyForm(@PathVariable("id") Project modifyproject, ModelMap model) {
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	Iterable<Person> persons = personService.findAll();
        model.addAttribute("modifyproject", modifyproject);        
		model.addAttribute(PERSON, person);
		model.addAttribute(PERSONS, persons);
        return new ModelAndView("formproject", model);
    }

    @PostMapping(value = "modify")
    public ModelAndView modifyForm(@ModelAttribute Project modifyproject, BindingResult result, ModelMap model) {
    	if (result.hasErrors()) {
            return new ModelAndView("formproject", "formErrors", result.getAllErrors());
        }
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());    		
	    model.addAttribute("modifyproject.id", modifyproject.getId());        
		model.addAttribute(PERSON, person);
		projectService.save(modifyproject);    	
        return new ModelAndView("redirect:/project/{modifyproject.id}", model);
    }

}
