package com.master.atrium.managementproject.service;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Task;

/**
 * Servicio de tarea
 * @author Rodrigo
 *
 */
public interface TaskService {
	/**
	 * Guarda una tarea dando como parámetro la tarea
	 * @param task
	 * @return
	 */
	public Task save(final Task task);
	/**
	 * Obtiene una tarea dando como parámetro el nombre
	 * @param name nombre de la tarea
	 * @return
	 */
	public Task findByName(String name);
	/**
	 * Obtiene una tarea dando como parámetro el identificador
	 * @param id identificador de la tarea
	 * @return
	 */
	public Task findById(Long id);
	/**
	 * Obtiene toda la lista de tareas
	 * @return
	 */
	public List<Task> findAll();
	/**
	 * Elimina una tarea dando como parámetro la tarea
	 * @param task la tarea
	 */
	public void delete(Task task);
	/**
	 * Obtiene una lista de tareas dando como parámetro la persona
	 * @param person la persona
	 * @return
	 */
	public List<Task> findTasksByPerson(Person person);
	/**
	 * Obtiene una lista de tareas dando como parámetro el proyecto
	 * @param project el proyecto
	 * @return
	 */
	public List<Task> findTasksByProject(Project project);
	
}
