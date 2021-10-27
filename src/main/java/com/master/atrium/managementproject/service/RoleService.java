package com.master.atrium.managementproject.service;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;

public interface RoleService {
	public Role findById(Long id);
	public List<Person> findPersonsByRole(Role role);
	public List<Role> findAll();
	public void delete(Role role);
	public Role save(Role role);
	public Role findByName(String name);	
}
