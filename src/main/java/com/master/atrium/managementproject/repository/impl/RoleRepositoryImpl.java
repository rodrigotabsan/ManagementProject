package com.master.atrium.managementproject.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.repository.RoleRepository;

/**
 * Implementación del repositorio de roles
 * @author Rodrigo
 *
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository{
	/** Inyección de {@link JdbcTemplate}*/
	@Autowired
	private JdbcTemplate template;

	/**
	 * Constructor de la clase
	 * @param template
	 */
	public RoleRepositoryImpl(JdbcTemplate template) {
		super();
		this.template = template;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void insert(Role role) {
		String query = "INSERT INTO role (name) VALUES (?);";
		template.update(query, role.getName());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void update(Role role) {
		String query = "UPDATE role SET name = ? WHERE id = ?;";
		template.update(query, role.getName(), role.getId());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		String query = "DELETE FROM role WHERE id = ?;";
		template.update(query, id);		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> findAll() {
		String query = "SELECT * FROM role;";
		return template.query(query, new BeanPropertyRowMapper<Role>(Role.class));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> findPersonsByIdRole(Long id) {
		String query = "SELECT p.id, p.name, p.lastname1, p.lastname2, p.user, p.email, p.password, p.role_id, p.dni, p.start_date, p.end_date FROM role r, person p WHERE r.id = ? AND r.id = p.role_id;";
		return template.query(query, new BeanPropertyRowMapper<Person>(Person.class), id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findById(Long id) {
		String query = "SELECT * FROM role WHERE id = ?;";
		List<Role> roles = template.query(query, new BeanPropertyRowMapper<Role>(Role.class), id);
		Role role = null;
		if(!roles.isEmpty()) {
			role = roles.get(0);
		}
		return role;
	}
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public Role findByName(String name) {
		String query = "SELECT * FROM role WHERE name = ?;";
		List<Role> roles = template.query(query, new BeanPropertyRowMapper<Role>(Role.class), name);
		Role role = null;
		if(!roles.isEmpty()) {
			role = roles.get(0);
		}
		return role;
	}
}
