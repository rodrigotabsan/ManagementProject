package com.master.atrium.managementproject.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.ProjectPerson;

/**
 * Interface de repositorio de proyecto/persona con las consultas a base de datos
 * @author Rodrigo
 *
 */
public interface ProjectPersonRepository {
	
	/**
	 * Crea la relación entre un proyecto y una persona y lo almacena en la tabla projectpersonlist
	 * @param project Proyecto a almacenar
	 * @param person Persona a almacenar
	 */
	public void insert(Project project, Person person);
	
	/**
	 * Actualiza la relación entre un proyecto y una persona
	 * @param project proyecto a almacenar
	 * @param person persona a almacenar
	 * @param projectBeforeUpdate proyecto antes de actualizarse
	 * @param personBeforeUpdate persona antes de actualizarse
	 */
	public void update(Project project, Person person, Project projectBeforeUpdate, Person personBeforeUpdate);
	
	/**
	 * Elimina la relación entre un proyecto y una persona
	 * @param project El proyecto
	 * @param person La persona
	 */
	public void delete(Project project, Person person);
	
	/**
	 * Encuentra todas las personas por identificador de proyecto
	 * @param id identificador del proyecto
	 * @return
	 */
	public List<Person> findAllPersonsByIdProject(Long id);
	
	/**
	 * Encuentra todos los proyectos por identificador de la persona
	 * @param id identificador de la persona
	 * @return
	 */
	public List<Project> findAllProjectsByIdPerson(Long id);
	
	/**
	 * Encuentra la relación entre una persona y un proyecto usando como parámetro el identificador de la persona y del proyecto
	 * @param idPerson identificador de la persona
	 * @param idProject identificador del proyecto
	 * @return
	 */
	public ProjectPerson findProjectPersonByIdPersonAndByIdProject(Long idPerson, Long idProject);	
}
