package com.master.atrium.managementproject.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.service.RoleService;

/**
 * Implementación del servicio de rol
 * @author Rodrigo
 *
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	/**
	 * Inyección de repositorio de rol
	 */
	@Autowired
	RoleRepository roleRepository;
	
	/**
	 * Constructor de la clase
	 */
	public RoleServiceImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findById(Long id) {
		return roleRepository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> findPersonsByRole(Role role) {
		List<Person> persons = new ArrayList<>();
		if(Objects.nonNull(role)) {
			persons = roleRepository.findPersonsByIdRole(role.getId());
			persons.forEach(person -> person.setRole(role));
		}
		return persons;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> findAll() {
		List<Role> roles = roleRepository.findAll();
		return Objects.nonNull(roles) ? roles : new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Role role) {
		roleRepository.deleteById(role.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role save(Role role) {
		Role roleFound = findByName(role.getName());
		if(Objects.nonNull(roleFound)) {
			roleRepository.update(role);
		} else {
			roleRepository.insert(role);
		}
		return roleRepository.findByName(role.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}
	
}
