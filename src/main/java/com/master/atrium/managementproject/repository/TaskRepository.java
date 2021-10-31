package com.master.atrium.managementproject.repository;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Task;

/**
 * Interface de repositorio de tareas con las consultas a base de datos
 * @author Rodrigo
 *
 */
public interface TaskRepository {
	
	/**
	 * Inserta una tarea
	 * @param task la tarea
	 */
	public void insert(Task task);
	
	/**
	 * Actualiza una tarea
	 * @param task la tarea
	 */
	public void update(Task task);
	
	/**
	 * Elimina una tarea usando como parámetro el identificador
	 * @param id identificador de la tarea
	 */
	public void deleteById(Long id);
	
	/**
	 * Obtiene una lista de tareas
	 * @return
	 */
	public List<Task> findAll();
	
	/**
	 * Obtiene una tarea usando como parámetro el identificador
	 * @param id identificador de la tarea
	 * @return
	 */
	public Task findById(Long id);
	
	/**
	 * Obtiene una tarea usando como parámetro el nombre
	 * @param name nombre de la tarea
	 * @return
	 */
	public Task findByName(String name);
	
	/**
	 * Obtiene una lista de las tareas utilizando el identificador del proyecto como parámetro
	 * @param projectId identificador del proyecto
	 * @return
	 */
	public List<Task> findTasksByProjectId(Long projectId);
	
	/**
	 * 	Obtiene una lista de las tareas utilizando el identificador de la persona como parámetro
	 * @param personId identificador de la persona
	 * @return
	 */
	public List<Task> findTasksByPersonId(Long personId);
	
}
