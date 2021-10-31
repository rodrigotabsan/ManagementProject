package com.master.atrium.managementproject.repository;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;

/**
 * Interface de repositorio de personas con las consultas a base de datos
 * @author Rodrigo
 *
 */
public interface PersonRepository {
	
	/**
	 * Crea una persona
	 * @param person La persona
	 */
	public void insert(Person person);
		
	/**
	 * Actualiza una persona
	 * @param person La persona
	 */
	public void update(Person person);
	
	/**
	 * Elimina una persona utilizando como parámetro su identificador
	 * @param id identificador de la persona
	 */
	public void deleteById(Long id);
	
	/**
	 * Encuentra una lista de personas
	 * @return
	 */
	public List<Person> findAll();
	
	/**
	 * Encuentra una persona usando como parámetro su identificador
	 * @param id identificador de la persona
	 * @return
	 */
	public Person findById(Long id);
		
	/**
	 * Encuentra una persona usando como parámetro su email
	 * @param email email de la persona
	 * @return
	 */
	public Person findByEmail(String email);
	
	/**
	 * Encuentra una persona usando como parámetro su usuario
	 * @param user usuario de la persona
	 * @return
	 */
	public Person findByUser(String user);
	
}
