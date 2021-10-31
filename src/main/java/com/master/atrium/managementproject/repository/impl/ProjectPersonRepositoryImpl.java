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
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectPersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;

/**
 * Implementación del repositorio de proyectos/personas
 * @author Rodrigo
 *
 */
@Repository
public class ProjectPersonRepositoryImpl implements ProjectPersonRepository{
	/** Inyección de {@link JdbcTemplate}*/
	@Autowired
	private JdbcTemplate template;
	/** Inyección de {@link PersonRepositoryImpl}*/
	@Autowired
	PersonRepository personRepository;
	/** Inyección de {@link ProjectRepositoryImpl}*/
	@Autowired
	ProjectRepository projectRepository;

	/**
	 * Constructor de la clase
	 * @param template
	 */
	public ProjectPersonRepositoryImpl(JdbcTemplate template) {
		super();
		this.template = template;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void insert(Project project, Person person) {
		String query = "INSERT INTO project_person_list (project_id, person_id) VALUES (?,?);";
		template.update(query, 
				project.getId(),
				person.getId());
	}
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void delete(Project project, Person person) {
		String query = "DELETE FROM project_person_list WHERE project_id = ? AND person_id = ?;";
		template.update(query, project.getId(), person.getId());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> findAllPersonsByIdProject(Long id) {
		List<Person> persons = new ArrayList<>();
		String query = "SELECT * FROM project_person_list WHERE project_id = ?;";
		List<ProjectPerson> projectsPersons = template.query(query, new BeanPropertyRowMapper<ProjectPerson>(ProjectPerson.class), id);
		for(ProjectPerson projectPerson : projectsPersons) { 
			persons.add(personRepository.findById(projectPerson.getPersonId()));
		}
		
		return persons;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Project> findAllProjectsByIdPerson(Long id) {
		List<Project> projects = new ArrayList<>();
		String query = "SELECT * FROM project_person_list WHERE person_id = ?;";
		List<ProjectPerson> projectsPersons = template.query(query, new BeanPropertyRowMapper<ProjectPerson>(ProjectPerson.class), id);
		for(ProjectPerson projectPerson : projectsPersons) {			
			projects.add(projectRepository.findById(projectPerson.getProjectId()));
		}
		
		return projects;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProjectPerson findProjectPersonByIdPersonAndByIdProject(Long idPerson, Long idProject) {
		String query = "SELECT * FROM project_person_list WHERE person_id = ? AND project_id = ?;";
		List<ProjectPerson> projectsPersons = template.query(query, new BeanPropertyRowMapper<ProjectPerson>(ProjectPerson.class), idPerson, idProject);
		ProjectPerson projectPerson = null;
		if(!projectsPersons.isEmpty()) {
			projectPerson = projectsPersons.get(0);
			Project project = projectRepository.findById(projectPerson.getProjectId());
			projectPerson.setProject(project);
			Person person = personRepository.findById(projectPerson.getPersonId());
			projectPerson.setPerson(person);
		}
		return projectPerson;
	}	
}
