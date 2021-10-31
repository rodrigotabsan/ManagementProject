package com.master.atrium.managementproject.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectPersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;
import com.master.atrium.managementproject.repository.TaskRepository;
import com.master.atrium.managementproject.service.ProjectService;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;

/**
 * Implementación del servicio de proyectos
 * 
 * @author Rodrigo
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	/**
	 * Inyección de repositorio de persona
	 */
	@Autowired
	PersonRepository personRepository;
	
	/**
	 * Inyección de repositorio de proyecto
	 */
	@Autowired
	ProjectRepository projectRepository;
	
	/**
	 * Inyección de repositorio de la relación entre proyecto y persona
	 */
	@Autowired
	ProjectPersonRepository projectPersonRepository;
	
	/**
	 * Inyección de repositorio de tareas
	 */
	@Autowired
	TaskRepository taskRepository;

	/**
	 * Constructor de la clase
	 */
	public ProjectServiceImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Project save(Project project) {
		Project projectFound = projectRepository.findById(project.getId());
		if (Objects.nonNull(projectFound)) {
			updateProject(project, projectFound);
		} else {
			insertProject(project);
		}
		return findByName(project.getName());
	}
	
	/**
	 * Actualiza un proyecto
	 * @param project datos del proyecto nuevos
	 * @param projectFound proyecto antes de actualizar
	 */
	private void updateProject(Project project, Project projectFound) {
		project.setTasks(taskRepository.findTasksByProjectId(projectFound.getId()));
		List<Person> personsOld = findAllPersonsByProject(projectFound);
		projectRepository.update(project);
		Project projectSaved = projectRepository.findByName(project.getName());
		if (Objects.nonNull(project.getPersons()) && project.getPersons().length > 0) {
			Person[] persons = mappingArrayIntegerPersonsToArrayPersons(project.getPersons());
			if (Objects.nonNull(personsOld)) {
				updateProjectPerson(project, projectSaved, projectFound, persons, personsOld);
			} else {
				insertProjectPerson(project, projectSaved, persons);
			}
		} else {
			deleteProjectPerson(project, projectSaved, personsOld);
		}
	}
	
	/**
	 * Crea la relación entre el proyecto y la persona
	 * @param project
	 * @param projectSaved
	 * @param persons
	 */
	private void insertProjectPerson(Project project, Project projectSaved, Person[] persons) {
		for (int indexProjects = 0; indexProjects < project.getPersons().length; indexProjects++) {
			projectPersonRepository.insert(projectSaved, persons[indexProjects]);
		}
	}
	
	/**
	 * Elimina la relación entre proyecto y persona
	 * @param project
	 * @param projectSaved
	 * @param personsOld
	 */
	private void deleteProjectPerson(Project project, Project projectSaved, List<Person> personsOld) {
		int indexPersons = 0;
		if (indexPersons >= project.getPersons().length) {
			while (indexPersons < personsOld.size()) {
				projectPersonRepository.delete(projectSaved, personsOld.get(indexPersons));
				indexPersons++;
			}
		}
	}
	
	/**
	 * Actualiza la relación entre persona y proyecto
	 * @param project
	 * @param projectSaved
	 * @param projectFound
	 * @param persons
	 * @param personsOld
	 */
	private void updateProjectPerson(Project project, Project projectSaved, Project projectFound, Person[] persons, List<Person> personsOld) {
		int indexPersons = 0;
		while (indexPersons < project.getPersons().length && indexPersons < personsOld.size()) {
			projectPersonRepository.update(projectSaved, persons[indexPersons], projectFound,
					personsOld.get(indexPersons));
			indexPersons++;
		}
		if (indexPersons >= project.getPersons().length) {
			while (indexPersons < personsOld.size()) {
				projectPersonRepository.delete(projectSaved, personsOld.get(indexPersons));
				indexPersons++;
			}
		} else if (indexPersons >= personsOld.size()) {
			while (indexPersons < project.getPersons().length) {
				projectPersonRepository.insert(projectSaved, persons[indexPersons]);
				indexPersons++;
			}
		}
	}
	
	/**
	 * Crea un nuevo proyecto
	 * @param project
	 */
	private void insertProject(Project project) {
		if (Objects.nonNull(project.getPersonList()) && project.getPersonList().isEmpty()) {
			project.setPersonList(null);
		}
		if (Objects.nonNull(project.getTasks()) && project.getTasks().isEmpty()) {
			project.setTasks(null);
		}
		projectRepository.insert(project);
		Project projectSaved = projectRepository.findByName(project.getName());
		if (Objects.nonNull(project.getPersons()) && project.getPersons().length > 0) {
			Person[] persons = mappingArrayIntegerPersonsToArrayPersons(project.getPersons());
			for (int indexProjects = 0; indexProjects < project.getPersons().length; indexProjects++) {
				projectPersonRepository.insert(projectSaved, persons[indexProjects]);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Project findByName(String name) {
		Project project = projectRepository.findByName(name);
		return addPersonListToProject(project);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Project findById(Long id) {
		Project project = projectRepository.findById(id);
		return addPersonListToProject(project);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Project> findAll() {
		List<Project> projects = projectRepository.findAll();
		for (Project project : projects) {
			project.setPersonList(findAllPersonsByProject(project));
		}
		return projects;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Project project) throws RecordReferencedInOtherTablesException {
		List<Person> persons = findAllPersonsByProject(project);
		if (!persons.isEmpty()) {
			throw new RecordReferencedInOtherTablesException(
					"Record is relationed with at least one person. Delete this reference to delete this record.");
		}
		projectRepository.deleteById(project.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> findAllPersonsByProject(Project project) {
		return projectPersonRepository.findAllPersonsByIdProject(project.getId());
	}

	/**
	 * Añade una lista de personas a un proyecto
	 * @param project
	 * @return
	 */
	private Project addPersonListToProject(Project project) {
		if (Objects.nonNull(project)) {
			project.setPersonList(findAllPersonsByProject(project));
		}
		return project;
	}

	/**
	 * Mapea los identificadores de personas recogidos del formulario y obtiene las personas 
	 * @param personsInteger
	 * @return
	 */
	private Person[] mappingArrayIntegerPersonsToArrayPersons(Integer[] personsInteger) {
		Person[] persons = new Person[personsInteger.length];
		for (int indexPersons = 0; indexPersons < personsInteger.length; indexPersons++) {
			persons[indexPersons] = getUserFromInteger(personsInteger[indexPersons]);
		}
		return persons;
	}

	/**
	 * Obtiene el usuario por el identificador de usuario
	 * @param personInteger
	 * @return
	 */
	private Person getUserFromInteger(Integer personInteger) {
		return personRepository.findById(Long.valueOf(personInteger));
	}

}
