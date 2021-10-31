package com.master.atrium.managementproject.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.ProjectPerson;
import com.master.atrium.managementproject.repository.ProjectPersonRepository;

@Repository
public class ProjectPersonRepositoryImpl implements ProjectPersonRepository{
	@Autowired
	private JdbcTemplate template;
	@Autowired
	PersonRepositoryImpl personRepositoryImpl;
	@Autowired
	ProjectRepositoryImpl projectRepositoryImpl;

	/**
	 * @param template
	 */
	public ProjectPersonRepositoryImpl(JdbcTemplate template) {
		super();
		this.template = template;
	}
	
	@Override
	@Transactional
	public void insert(Project project, Person person) {
		String query = "INSERT INTO project_person_list (project_id, person_id) VALUES (?,?);";
		template.update(query, 
				project.getId(),
				person.getId());
	}
	
	@Override
	@Transactional
	public void update(Project project, Person person, Project projectBeforeUpdate, Person personBeforeUpdate) {
		String query = "UPDATE project_person_list SET project_id = ?, person_id = ? WHERE project_id = ? AND person_id = ?;";
		template.update(query, 
						project.getId(),
						person.getId(), 
						projectBeforeUpdate.getId(),
						personBeforeUpdate.getId());
		
	}
	
	@Override
	@Transactional
	public void delete(Project project, Person person) {
		String query = "DELETE FROM project_person_list WHERE project_id = ? AND person_id = ?;";
		template.update(query, project.getId(), person.getId());
	}
	
	@Override
	public List<Person> findAllPersonsByIdProject(Long id) {
		List<Person> persons = new ArrayList<>();
		String query = "SELECT * FROM project_person_list WHERE project_id = ?;";
		List<ProjectPerson> projectsPersons = template.query(query, new BeanPropertyRowMapper<ProjectPerson>(ProjectPerson.class), id);
		for(ProjectPerson projectPerson : projectsPersons) { 
			persons.add(personRepositoryImpl.findById(projectPerson.getPersonId()));
		}
		
		return persons;
	}
	
	@Override
	public List<Project> findAllProjectsByIdPerson(Long id) {
		List<Project> projects = new ArrayList<>();
		String query = "SELECT * FROM project_person_list WHERE person_id = ?;";
		List<ProjectPerson> projectsPersons = template.query(query, new BeanPropertyRowMapper<ProjectPerson>(ProjectPerson.class), id);
		for(ProjectPerson projectPerson : projectsPersons) {			
			projects.add(projectRepositoryImpl.findById(projectPerson.getProjectId()));
		}
		
		return projects;
	}
	
	@Override
	public ProjectPerson findProjectPersonByIdPersonAndByIdProject(Long idPerson, Long idProject) {
		String query = "SELECT * FROM project_person_list WHERE person_id = ? AND project_id = ?;";
		List<ProjectPerson> projectsPersons = template.query(query, new BeanPropertyRowMapper<ProjectPerson>(ProjectPerson.class), idPerson, idProject);
		ProjectPerson projectPerson = null;
		if(!projectsPersons.isEmpty()) {
			projectPerson = projectsPersons.get(0);
			Project project = projectRepositoryImpl.findById(projectPerson.getProjectId());
			projectPerson.setProject(project);
			Person person = personRepositoryImpl.findById(projectPerson.getPersonId());
			projectPerson.setPerson(person);
		}
		return projectPerson;
	}	
}
