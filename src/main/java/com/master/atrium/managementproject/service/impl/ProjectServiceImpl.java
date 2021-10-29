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
 * @author Rodrigo
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	PersonRepository personRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ProjectPersonRepository projectPersonRepository;
	@Autowired
	TaskRepository taskRepository;
	/**
	 * Constructor de la clase
	 */
	public ProjectServiceImpl() {
		super();
	}

	@Override
	public Project save(Project project) {
		Project projectFound = projectRepository.findById(project.getId());
		if(Objects.nonNull(projectFound)) {
			project.setTasks(taskRepository.findTasksByProjectId(projectFound.getId()));			
			List<Person> personsOld = findAllPersonsByProject(projectFound);
			projectRepository.update(project);
			Project projectSaved = projectRepository.findByName(project.getName());
			if(Objects.nonNull(project.getPersons()) && project.getPersons().length > 0) {
				if(Objects.nonNull(personsOld)){
					int indexPersons = 0;
					while(indexPersons < project.getPersons().length && indexPersons < personsOld.size()) {
						projectPersonRepository.update(projectSaved, project.getPersons()[indexPersons], projectFound, personsOld.get(indexPersons));
						indexPersons++;
					}
					if(indexPersons >= project.getPersons().length) {
						while(indexPersons < personsOld.size()) {
							projectPersonRepository.delete(projectSaved, personsOld.get(indexPersons));
							indexPersons++;
						}
					} else if(indexPersons >= personsOld.size()) {
						while(indexPersons < project.getPersons().length) {
							projectPersonRepository.insert(projectSaved, project.getPersons()[indexPersons]);
							indexPersons++;
						}
					}
				} else {
					for(int indexProjects = 0; indexProjects < project.getPersons().length; indexProjects++) {
						projectPersonRepository.insert(projectSaved, project.getPersons()[indexProjects]);
					}
				}
			}
		} else {
			if(Objects.nonNull(project.getPersonList()) && project.getPersonList().isEmpty()) {
				project.setPersonList(null);
			}
			if(Objects.nonNull(project.getTasks()) && project.getTasks().isEmpty()) {
				project.setTasks(null);
			}
			projectRepository.insert(project);
			Project projectSaved = projectRepository.findByName(project.getName());
			if(Objects.nonNull(project.getPersons()) && project.getPersons().length > 0) {
				for(int indexProjects = 0; indexProjects < project.getPersons().length; indexProjects++) {
					projectPersonRepository.insert(projectSaved, project.getPersons()[indexProjects]);
				}
			}
		}		
        return findByName(project.getName());
	}
	
	@Override
	public Project findByName(String name) {
		Project project = projectRepository.findByName(name);
		return addPersonListToProject(project);
	}
	
	@Override
	public Project findById(Long id) {
		Project project = projectRepository.findById(id); 
		return addPersonListToProject(project); 
	}
	
	@Override
	public List<Project> findAll() {
		List<Project> projects = projectRepository.findAll();
		for(Project project : projects) {
			project.setPersonList(findAllPersonsByProject(project));			
		}
		return projects;
	}

	@Override
	public void delete(Project project) throws RecordReferencedInOtherTablesException {
		List<Person> persons = findAllPersonsByProject(project);
		if(!persons.isEmpty()) {
			throw new RecordReferencedInOtherTablesException("Record is relationed with at least one person. Delete this reference to delete this record.");
		}
		projectRepository.deleteById(project.getId());		
	}

	@Override
	public List<Person> findAllPersonsByProject(Project project) {		
		return projectPersonRepository.findAllPersonsByIdProject(project.getId());
	}
	
	private Project addPersonListToProject(Project project) {
		if(Objects.nonNull(project)) {
			project.setPersonList(findAllPersonsByProject(project));
		}
		return project;
	}
		
}
