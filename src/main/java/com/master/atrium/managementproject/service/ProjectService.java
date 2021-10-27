package com.master.atrium.managementproject.service;

import java.util.List;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;

public interface ProjectService {
	public Project save(final Project project);
	public Project findByName(String name);
	public Project findById(Long id);
	public List<Project> findAll();
	public void delete(Project project) throws RecordReferencedInOtherTablesException;
	public List<Person> findAllPersonsByProject(final Project project);	
}
