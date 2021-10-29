package com.master.atrium.managementproject.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Task;
import com.master.atrium.managementproject.repository.MessageRepository;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;
import com.master.atrium.managementproject.repository.TaskRepository;
import com.master.atrium.managementproject.service.TaskService;

/**
 * Implementación del servicio de tareas
 * @author Rodrigo
 *
 */
@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	ProjectRepository projectRepository;
	
	/**
	 * Constructor de la clase
	 */
	public TaskServiceImpl() {
		super();
	}

	@Override
	public Task save(Task task) {
		Task taskFound = taskRepository.findByName(task.getName());
		if(Objects.nonNull(taskFound)) {
			taskRepository.update(setPersonIdAndProjectId(task));
		} else {
			taskRepository.insert(setPersonIdAndProjectId(task));
		}
		return findByName(task.getName());
	}

	@Override
	public Task findByName(String name) {
		Task task = taskRepository.findByName(name);
		return setPersonAndProjectAndMessages(task);
	}

	@Override
	public Task findById(Long id) {
		Task task = taskRepository.findById(id);
		return setPersonAndProjectAndMessages(task);
	}

	@Override
	public List<Task> findAll() {
		List<Task> tasks = taskRepository.findAll();
		return setPersonAndProjectAndMessagesToListOfTasks(tasks);
	}

	@Override
	public void delete(Task task) {
		Task taskFound = findByName(task.getName());
		if(Objects.nonNull(taskFound)) {		
			taskRepository.deleteById(taskFound.getId());			
		}
	}

	@Override
	public List<Task> findTasksByPerson(Person person) {
		List<Task> tasks = taskRepository.findTasksByPersonId(person.getId());
		return setPersonAndProjectAndMessagesToListOfTasks(tasks);
	}

	@Override
	public List<Task> findTasksByProject(Project project) {
		List<Task> tasks = taskRepository.findTasksByProjectId(project.getId());		
		return setPersonAndProjectAndMessagesToListOfTasks(tasks);
	}
	
	private List<Task> setPersonAndProjectAndMessagesToListOfTasks(List<Task> tasks){
		for(Task task : tasks) {			
			setPersonAndProjectAndMessages(task);		
		}
		return tasks;
	}
	
	private Task setPersonAndProjectAndMessages(Task task) {
		List<Message> messages = null;
		Person personToAdd = null;
		Project projectToAdd = null;
		if(Objects.nonNull(task)) {
			if(Objects.nonNull(task.getPersonId())) {
				personToAdd = personRepository.findById(task.getPersonId());
				task.setPerson(personToAdd);
			}
			if(Objects.nonNull(task.getProjectId())) {
				projectToAdd = projectRepository.findById(task.getProjectId());
				task.setProject(projectToAdd);
			}
			if(Objects.nonNull(task.getId())) {
				messages = messageRepository.findMessagesByTaskId(task.getId());
			}
			if(Objects.nonNull(messages)) {
				task.setMessages(messages);
			}
		}
		return task;
	}
	
	private Task setPersonIdAndProjectId(Task task) {
		if(Objects.nonNull(task.getPerson()) 
				&& Objects.nonNull(task.getPerson().getId())) {
			task.setPersonId(task.getPerson().getId());
		}
		if(Objects.nonNull(task.getProject()) 
				&& Objects.nonNull(task.getProject().getId())) {
			task.setProjectId(task.getProject().getId());
		}
		return task;
	}
}
