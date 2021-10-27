package com.master.atrium.managementproject.service;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

public interface PersonService {
	public Person save(final Person person) throws EmailExistsException, UserExistsException;
	public void delete(final Person person) throws RecordReferencedInOtherTablesException;
	public List<Person> findAll();
	public List<Project> findAllProjectsByPerson(final Person person);
	public Person findById(final Long id);
	public Person findByEmail(final String email);
	public Person findByUser(final String user);	
}
