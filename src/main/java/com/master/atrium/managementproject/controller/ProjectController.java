package com.master.atrium.managementproject.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

/**
 * Controlador para las acciones con los proyectos
 * @author Rodrigo
 *
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

	/** Constante de PERSON */
	private static final String PERSON = "person";
	/** Constante de PERSONS */
	private static final String PERSONS = "persons";
	
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
	 * Inyección de {@link ProjectService}
	 */
	@Autowired
	ProjectService projectService;
	
	/**
	 * Constructor de la clase
	 */
	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	/**
	 * Inicializador del controlador. Cada acción que se ejecute pasará primero por esta función.
	 * @param binder {@link WebDataBinder}
	 */
	@InitBinder     
	public void initBinder(WebDataBinder binder){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
	}
	
	/**
	 * Petición GET que obtiene la lista de todos los proyectos
	 * @param model
	 * @return
	 */
	@GetMapping
    public ModelAndView list(ModelMap model) {        
        Iterable<Project> projects = this.projectService.findAll();
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
        model.addAttribute("projects", projects);        
		model.addAttribute(PERSON, person);
        return new ModelAndView("listproject", model);
    }

	/**
	 * Petición GET que obtiene la vista de un proyecto por identificador
	 * @param idViewProject Identificador del proyecto a ver
	 * @param model
	 * @return
	 */
	@GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long idViewProject, ModelMap model) {
		Project viewProject = projectService.findById(idViewProject);
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
        List<Person> persons = projectService.findAllPersonsByProject(viewProject);    
        model.addAttribute("viewproject", viewProject);        
		model.addAttribute(PERSON, person);
		model.addAttribute(PERSONS, persons);
        return new ModelAndView("viewproject", model);
    }

	/**
	 * Petición GET que prepara el formulario de crear proyecto
	 * @param project Proyecto a crear
	 * @param result Resultado de la validación para la creación de un formulario.
	 * @param model
	 * @return
	 */
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
    
    /**
     * Crea un proyecto
     * @param createproject proyecto a crear
     * @param result Valida los datos del formulario de creación
     * @param model
     * @return
     */
    @PostMapping(value = "createproject")
    public ModelAndView create(@Valid Project createproject, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return new ModelAndView("registrationproject", "formErrors", result.getAllErrors());
        }
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());               
		
		Project projectcreated = projectService.save(createproject);		
		
		model.addAttribute("viewproject.id", projectcreated.getId()); 
		model.addAttribute(PERSON, person);
        model.addAttribute("globalMessage", "Successfully created a new project");
        return new ModelAndView("redirect:/project/{viewproject.id}", model);
    }

    /**
     * Elimina un usuario por su identificador
     * @param id identificador del usuario a eliminar
     * @return
     * @throws RecordReferencedInOtherTablesException Exception en caso de que el campo a borrar tenga algún dato relacionado con otra tabla. Por ejemplo, con ProjectPersonList
     */
    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) throws RecordReferencedInOtherTablesException {
    	Project project = projectService.findById(id);
        this.projectService.delete(project);
        return new ModelAndView("redirect:/project/");
    }

    /**
     * Crea el formulario de modificación para un proyecto
     * @param idModifyproject identificador del proyecto a modificar
     * @param model
     * @return
     */
    @GetMapping(value = "viewmodify/{id}")
    public ModelAndView viewmodifyForm(@PathVariable("id") Long idModifyproject, ModelMap model) {
    	Project modifyproject = projectService.findById(idModifyproject);
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	Collection<Person> allPersons = personService.findAll();
    	Collection<Person> personsSelected = projectService.findById(modifyproject.getId()).getPersonList();
    	
    	Set<Long> idsProjSelected = personsSelected.stream()
    	        .map(Person::getId)
    	        .collect(Collectors.toSet());
    	Set<Person> personsNotSelected = allPersons.stream()
    	        .filter(proj -> !idsProjSelected.contains(proj.getId()))
    	        .collect(Collectors.toSet());
        model.addAttribute("modifyproject", modifyproject);        
		model.addAttribute(PERSON, person);
		model.addAttribute(PERSONS, personsNotSelected);
		model.addAttribute("personsselected", personsSelected);
        return new ModelAndView("formproject", model);
    }

    /**
     * Petición POST para modificar un proyecto
     * @param modifyproject
     * @param result
     * @param model
     * @return
     */
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
