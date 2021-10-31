package com.master.atrium.managementproject.service;

import java.util.Date;
import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

/**
 * Servicio de persona
 * @author Rodrigo
 *
 */
public interface PersonService {
	/**
	 * Guarda una persona
	 * @param person la persona a guardar
	 * @return
	 * @throws EmailExistsException Excepción en caso de que el correo exista ya.
	 * @throws UserExistsException Excepción en caso de que el nombre de usuario exista ya.
	 */
	public Person save(final Person person) throws EmailExistsException, UserExistsException;
	/**
	 * Elimina una persona
	 * @param person
	 * @throws RecordReferencedInOtherTablesException
	 */
	public void delete(final Person person) throws RecordReferencedInOtherTablesException;
	/**
	 * Obtiene una lista de personas
	 * @return
	 */
	public List<Person> findAll();
	/**
	 * Obtiene una lista de proyectos de una persona pasando la persona como parámetro
	 * @param person la persona
	 * @return
	 */
	public List<Project> findAllProjectsByPerson(final Person person);
	/**
	 * Obtiene una persona por su identificador
	 * @param id identificador de la persona
	 * @return
	 */
	public Person findById(final Long id);
	/**
	 * Obtiene una persona por su email
	 * @param email email de la persona
	 * @return
	 */
	public Person findByEmail(final String email);
	/**
	 * Obtiene una persona por su nombre de usuario
	 * @param user nombre de usuario de la persona
	 * @return
	 */
	public Person findByUser(final String user);
	
	/**
	 * Obtiene un array de fechas de comienzo de proyectos de una persona
	 * @param person persona involucrada en los proyectos
	 */
	public Date[] getArrayStartDates(Person person);
	
	/**
	 * Obtiene un array de fechas de finalización de proyectos de una persona
	 * @param person persona involucrada en los proyectos
	 */
	public Date[] getArrayEndDates(Person person);
}
