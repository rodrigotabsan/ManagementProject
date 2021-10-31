package com.master.atrium.managementproject.service;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;

/**
 * Servicio de proyecto
 * @author Rodrigo
 *
 */
public interface ProjectService {
	/**
	 * Guarda un proyecto
	 * @param project proyecto
	 * @return
	 */
	public Project save(final Project project);
	/**
	 * Obtiene un proyecto por su nombre
	 * @param name nombre del proyecto
	 * @return
	 */
	public Project findByName(String name);
	/**
	 * Obtiene un proyecto por su identificador
	 * @param id identificador del proyecto
	 * @return
	 */
	public Project findById(Long id);
	/**
	 * Obtiene toda la lista de proyectos
	 * @return
	 */
	public List<Project> findAll();
	/**
	 * Elimina un proyecto pasando como parámetro el proyecto
	 * @param project el proyecto
	 * @throws RecordReferencedInOtherTablesException Lanza una excepción en el caso de que haya una relación entre este proyecto y una persona
	 */
	public void delete(Project project) throws RecordReferencedInOtherTablesException;
	/**
	 * Encuentra todas personas para este proyecto pasando como parámetro el proyecto
	 * @param project el proyecto
	 * @return
	 */
	public List<Person> findAllPersonsByProject(final Project project);	
}
