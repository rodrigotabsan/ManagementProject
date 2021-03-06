package com.master.atrium.managementproject.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.master.atrium.managementproject.dto.TaskMessageDto;
import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Task;
import com.master.atrium.managementproject.service.MessageService;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.service.ProjectService;
import com.master.atrium.managementproject.service.TaskService;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;
import com.master.atrium.managementproject.utility.UtilEMail;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;

@Controller
@RequestMapping("/task")
public class TaskController {

	private static final String PERSON = "person";
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	/**
	 * Inicializador del controlador. Cada acci??n que se ejecute pasar?? primero por esta funci??n.
	 * @param binder {@link WebDataBinder}
	 */
	@InitBinder     
	public void initBinder(WebDataBinder binder){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
	}
	
	@GetMapping
	public ModelAndView list(ModelMap model) {        
        Iterable<Task> tasks = this.taskService.findAll();
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
        model.addAttribute("tasks", tasks);        
		model.addAttribute(PERSON, person);
        return new ModelAndView("listtask", model);
    }

	/**
	 * Petici??n GET que obtiene la vista de un proyecto por identificador
	 * @param idViewProject Identificador del proyecto a ver
	 * @param model
	 * @return
	 */
	@GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long idViewTask, ModelMap model) {
		Task viewTask = taskService.findById(idViewTask);
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
        List<Message> messages = messageService.findMessagesByTask(viewTask);    
        model.addAttribute("viewtask", viewTask);        
		model.addAttribute(PERSON, person);
		model.addAttribute("messages", messages);
        return new ModelAndView("viewtask", model);
    }

	/**
	 * Petici??n GET que prepara el formulario de crear tarea
	 * @param tarea Tarea a crear
	 * @param result Resultado de la validaci??n para la creaci??n de un formulario.
	 * @param model
	 * @return
	 */
    @GetMapping(value = "createform")
    public ModelAndView createForm(@Valid Task project, BindingResult result, ModelMap model) {
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	Task createtask = new Task();
    	List<Project> projects = personService.findAllProjectsWithTheirOwnPersonsByPerson(person);
    	model.addAttribute(PERSON, person);
    	model.addAttribute("projects", projects);
    	model.addAttribute("createtask", createtask);
    	return new ModelAndView("registrationtask", model);
    }
        
    /**
     * Crea una tarea
     * @param createtarea tarea a crear
     * @param result Valida los datos del formulario de creaci??n
     * @param model
     * @return
     */
    @PostMapping(value = "createtask")
    public ModelAndView create(@Valid Task createtask, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return new ModelAndView("registrationtask", "formErrors", result.getAllErrors());
        }
        Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());               
		
		Task taskcreated = taskService.save(createtask);		
		sendEmail(person, taskcreated);
		model.addAttribute("viewtask.id", taskcreated.getId()); 
		model.addAttribute(PERSON, person);
        return new ModelAndView("redirect:/task/{viewtask.id}", model);
    }

    /**
     * Elimina una tarea por su identificador
     * @param id identificador de la tarea a eliminar
     * @return
     * @throws RecordReferencedInOtherTablesException Exception en caso de que el campo a borrar tenga alg??n dato relacionado con otra tabla. Por ejemplo, con Project
     */
    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) throws RecordReferencedInOtherTablesException {
    	Task task = taskService.findById(id);
    	this.messageService.deleteAllByTask(task);    	
        this.taskService.delete(task);
        return new ModelAndView("redirect:/task/");
    }

    /**
     * Crea el formulario de modificaci??n para una tarea
     * @param idModifyproject identificador de la tarea a modificar
     * @param model
     * @return
     */
    @GetMapping(value = "viewmodify/{id}")
    public ModelAndView viewmodifyForm(@PathVariable("id") Long idModifyTask, ModelMap model) {    	
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	TaskMessageDto taskMessageDto = new TaskMessageDto(taskService.findById(idModifyTask), new Message());
    	List<Project> projects = personService.findAllProjectsWithTheirOwnPersonsByPerson(person);
    	model.addAttribute(PERSON, person);
    	model.addAttribute("projects", projects);
        model.addAttribute("taskmessagedto", taskMessageDto);
        return new ModelAndView("formtask", model);
    }

    /**
     * Petici??n POST para modificar una tarea
     * @param taskMessageDto
     * @param result
     * @param model
     * @return
     */
    @PostMapping(value = "modify")
    public ModelAndView modifyForm(@ModelAttribute TaskMessageDto taskmessagedto, BindingResult result, ModelMap model) {
    	if (result.hasErrors()) {
            return new ModelAndView("formproject", "formErrors", result.getAllErrors());
        }
    	Person person = personService.findByUser(userDetailsService.getUserDetails().getUsername());
    	Long idTask = taskmessagedto.getTask().getId();
    	taskService.save(taskmessagedto.getTask());
    	taskmessagedto.getMessage().setTaskId(idTask);
    	taskmessagedto.getMessage().setTask(taskmessagedto.getTask());
    	messageService.save(taskmessagedto.getMessage());
    	
	    model.addAttribute("modifytask.id", idTask);        
		model.addAttribute(PERSON, person);  	
        return new ModelAndView("redirect:/task/{modifytask.id}", model);
    }

    /**
     * Enviar un correo cuando se cree una tarea
     * @param person
     */
    private void sendEmail(Person person, Task task) {
    	UtilEMail emailUtil = new UtilEMail();
		List<String> emails = new ArrayList<>();
		emails.add(person.getEmail());
		emailUtil.sendEmail(emails, new Date() + ": A task was created." , "Task "+task.getName()+" has been created on your name. '"+task.getDescription()+"'");
    }
   
}
