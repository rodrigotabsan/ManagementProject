package com.master.atrium.managementproject.service;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Task;

public interface TaskService {
	public Task save(final Task task);
	public Task findByName(String name);
	public Task findById(Long id);
	public List<Task> findAll();
	public void delete(Task task);
	public List<Task> findTasksByPerson(Person person);
	public List<Task> findTasksByProject(Project project);	
}
