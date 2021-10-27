package com.master.atrium.managementproject.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;

@Repository
public class RoleRepository {
	@Autowired
	private JdbcTemplate template;

	/**
	 * @param template
	 */
	public RoleRepository(JdbcTemplate template) {
		super();
		this.template = template;
	}

	@Transactional
	public void insert(Role role) {
		String query = "INSERT INTO role (name) VALUES (?);";
		template.update(query, role.getName());
	}
	
	@Transactional
	public void update(Role role) {
		String query = "UPDATE role SET name = ? WHERE id = ?;";
		template.update(query, role.getName(), role.getId());
	}
	
	@Transactional
	public void deleteById(Long id) {
		String query = "DELETE FROM role WHERE id = ?;";
		template.update(query, id);		
	}
	
	public List<Role> findAll() {
		String query = "SELECT * FROM role;";
		return template.query(query, new BeanPropertyRowMapper<Role>(Role.class));
	}
	
	public List<Person> findPersonsByIdRole(Long id) {
		String query = "SELECT p.id, p.name, p.lastname1, p.lastname2, p.user, p.email, p.password, p.role_id, p.dni, p.start_date, p.end_date FROM role r, person p WHERE r.id = ? AND r.id = p.role_id;";
		return template.query(query, new BeanPropertyRowMapper<Person>(Person.class), id);
	}
	
	public Role findById(Long id) {
		String query = "SELECT * FROM role WHERE id = ?;";
		List<Role> roles = template.query(query, new BeanPropertyRowMapper<Role>(Role.class), id);
		Role role = null;
		if(!roles.isEmpty()) {
			role = roles.get(0);
		}
		return role;
	}
		
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
