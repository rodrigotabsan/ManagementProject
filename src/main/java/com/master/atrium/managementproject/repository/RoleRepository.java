package com.master.atrium.managementproject.repository;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;

/**
 * Interface de repositorio de roles con las consultas a base de datos
 * @author Rodrigo
 *
 */
public interface RoleRepository {
	
	/**
	 * Crea un rol
	 * @param role El rol
	 */
	public void insert(Role role);
	
	/**
	 * Actualiza un rol
	 * @param role
	 */
	public void update(Role role);
	
	/**
	 * Elimina un rol utilizando como parámetro su identificador
	 * @param id identificador del rol
	 */
	public void deleteById(Long id);
	
	/**
	 * Obtiene toda la lista de roles
	 * @return
	 */
	public List<Role> findAll();
	
	/**
	 * Obtiene la lista de personas utilizando como parámetro el identificador del rol
	 * @param id el identificador del rol
	 * @return
	 */
	public List<Person> findPersonsByIdRole(Long id);
	
	/**
	 * Obtiene un rol por su identificador
	 * @param id identificador del rol
	 * @return
	 */
	public Role findById(Long id);
	
	/**
	 * Obtiene un rol por su nombre
	 * @param name nombre del rol
	 * @return
	 */
	public Role findByName(String name);
}
