package com.master.atrium.managementproject.service;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;

/**
 * Servicio de rol
 * @author Rodrigo
 *
 */
public interface RoleService {
	/**
	 * Obtiene el rol dando como parámetro el identificador
	 * @param id identificador del rol
	 * @return
	 */
	public Role findById(Long id);
	/**
	 * Obtiene una lista de personas dando como parámetro el rol
	 * @param role Rol
	 * @return
	 */
	public List<Person> findPersonsByRole(Role role);
	/**
	 * Obtiene toda la lista de roles
	 * @return
	 */
	public List<Role> findAll();
	/**
	 * Elimina un rol dando como parámetro el rol
	 * @param role Rol
	 */
	public void delete(Role role);
	/**
	 * Guarda un rol dando como parámetro el rol
	 * @param role
	 * @return
	 */
	public Role save(Role role);
	/**
	 * Obtiene el rol dando como parámetro el nombre del rol
	 * @param name nombre del rol
	 * @return
	 */
	public Role findByName(String name);	
}
