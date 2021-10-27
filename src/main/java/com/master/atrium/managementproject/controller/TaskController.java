package com.master.atrium.managementproject.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Task;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.ProjectService;
import com.master.atrium.managementproject.service.TaskService;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping
	public ModelAndView list(ModelMap model) {        
        Iterable<Task> tasks = this.taskService.findAll();
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
        model.addAttribute("tasks", tasks);        
		model.addAttribute("person", person);
        return new ModelAndView("listtask", model);
    }

	@GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Person person) {
        return new ModelAndView("viewtask", "task", person);
    }

    @PostMapping()
    public ModelAndView create(@Valid Person person, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("formtask", "formErrors", result.getAllErrors());
        }
        person = this.personService.save(person);
        redirect.addFlashAttribute("globalMessage", "Successfully created a new task");
        return new ModelAndView("redirect:/task/{task.id}", "task.id", person.getId());
    }

    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.personService.deleteById(id);
        return new ModelAndView("redirect:/task/");
    }

    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Person person) {
        return new ModelAndView("formtask", "task", person);
    }

    // the form

    @GetMapping(params = "form")
    public String createForm(@ModelAttribute Person person) {
        return "registration";
    }

}
