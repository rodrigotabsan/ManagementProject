package com.master.atrium.managementproject.repository;

import java.util.List;

import com.master.atrium.managementproject.entity.Project;

/**
 * Interface de repositorio de proyectos con las consultas a base de datos
 * @author Rodrigo
 *
 */
public interface ProjectRepository {
	
	/**
	 * Crea un proyecto
	 * @param project
	 */
	public void insert(Project project);
	
	/**
	 * Actualiza un proyecto
	 * @param project
	 */
	public void update(Project project);
	
	/**
	 * Elimina un proyecto usando el identificador del proyecto como parámetro
	 * @param id identificador del proyecto
	 */
	public void deleteById(Long id);
	
	/**
	 * Encuentra una lista de todos los proyectos
	 * @return
	 */
	public List<Project> findAll();
	
	/**
	 * Encuentra un proyecto usando como parámetro su identificador
	 * @param id identificador del proyecto
	 * @return
	 */
	public Project findById(Long id);
		
	/**
	 * Encuentra un proyecto usando como parámetro su nombre
	 * @param name nombre del proyecto.
	 * @return
	 */
	public Project findByName(String name);
}
