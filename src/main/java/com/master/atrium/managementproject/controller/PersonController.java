package com.master.atrium.managementproject.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.ProjectService;
import com.master.atrium.managementproject.service.RoleService;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;
import com.master.atrium.managementproject.utility.UtilEMail;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

/**
 * Controlador para las acciones con personas
 * @author Rodrigo
 *
 */
@Controller
@RequestMapping("/person")
public class PersonController {
	/** Constante PERSON*/
	private static final String PERSON = "person";
	/** Constante PROJECTS*/
	private static final String PROJECTS = "projects";
	/** Constante VIEWPERSON*/
	private static final String VIEWPERSON = "viewperson";
	/**
	 * Inyecci??n {@link UserDetailsServiceImpl}
	 */
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	/**
	 * Inyecci??n {@link PersonService}
	 */
	@Autowired
	PersonService personService;
	/**
	 * Inyecci??n {@link ProjectService}
	 */
	@Autowired
	ProjectService projectService;
	/**
	 * Inyecci??n {@link RoleRepository}
	 */
	@Autowired
	RoleService roleService;
	/**
	 * Constructor de la clase
	 */
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	/**
	 * Obtiene toda la lista de personas
	 * @param model
	 * @return
	 */
	@GetMapping
    public ModelAndView list(ModelMap model) {
        Iterable<Person> persons = this.personService.findAll();
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());        
        model.addAttribute("persons", persons);        
		model.addAttribute(PERSON, person);		
        return new ModelAndView("listperson", model);
    }

	/**
	 * Obtiene el usuario que quiere ser visualizado
	 * @param idViewperson identificador de la persona a mostrar
	 * @param model
	 * @return
	 */
	@GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long idViewperson, ModelMap model) {
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
        Person viewperson = personService.findById(idViewperson);
        Iterable<Project> projects = viewperson.getProjectList();
        model.addAttribute(VIEWPERSON, viewperson);        
		model.addAttribute(PERSON, person);
		model.addAttribute(PROJECTS, projects);
        return new ModelAndView(VIEWPERSON, model);
    }
	
	/**
	 * Prepara el formulario de crear usuario
	 * @param model
	 * @return
	 */
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

	/**
	 * Petici??n post que guarda una persona
	 * @param createperson Persona que va a ser creada
	 * @param result Captura los errores que pudiera haber al guardar el formulario
	 * @param model
	 * @return
	 * @throws EmailExistsException Si el email ya existe env??a un error al guardar
	 * @throws UserExistsException Si el usuario ya existe env??a un error al guardar
	 */
    @PostMapping(value = "createperson")
    public ModelAndView create(@Valid Person createperson, BindingResult result, ModelMap model) throws EmailExistsException, UserExistsException {
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	
    	if (result.hasErrors()) {
            return new ModelAndView("registrationperson", "formErrors", result.getAllErrors());
        }         		
		personService.save(createperson);
		Person viewperson = personService.findByUser(createperson.getUser());
		sendEmail(createperson);
		model.addAttribute(PERSON, person);
		model.addAttribute(VIEWPERSON, viewperson); 
        return new ModelAndView("redirect:/person/"+ viewperson.getId());
    }

    /**
     * Petici??n GET que elimina un usuario
     * @param id identificador del usuario a ser eliminado
     * @return
     * @throws RecordReferencedInOtherTablesException Exception en caso de que el campo a borrar tenga alg??n dato relacionado con otra tabla. Por ejemplo, con ProjectPersonList
     */
    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) throws RecordReferencedInOtherTablesException {
    	Person person = personService.findById(id);
        this.personService.delete(person);
        return new ModelAndView("redirect:/person/");
    }
    
    /**
     * Prepara el formulario de modificaci??n.
     * @param idModifyperson Identificador de la persona a modificar
     * @param model
     * @return
     */
    @GetMapping(value = "viewmodify/{id}")
    public ModelAndView viewmodifyForm(@PathVariable("id") Long idModifyperson, ModelMap model) {
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	Person modifyperson = personService.findById(idModifyperson);
    	Iterable<Role> roles = roleService.findAll();
    	
    	Collection<Project> allprojects = projectService.findAll();
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

    /**
     * Petici??n POST para la modificaci??n de una persona.
     * @param modifyperson Persona a modificar
     * @param result Obtiene los errores producidos tras obtener los datos del formulario.
     * @param model
     * @return
     * @throws EmailExistsException Si el email ya existe env??a un error al guardar
	 * @throws UserExistsException Si el usuario ya existe env??a un error al guardar
     */
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
    
    /**
     * Enviar un correo cuando se cree un usuario
     * @param person el usuario
     */
    private void sendEmail(Person person) {
    	UtilEMail emailUtil = new UtilEMail();
		List<String> emails = new ArrayList<>();
		emails.add(person.getEmail());
		emailUtil.sendEmail(emails, new Date() + ": Your account was created." , "This is an email to let you know that your account has been created. Remember that your username is "+person.getUser()+".");
    }

}
