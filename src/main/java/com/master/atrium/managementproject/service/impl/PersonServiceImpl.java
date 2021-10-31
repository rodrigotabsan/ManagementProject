package com.master.atrium.managementproject.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectPersonRepository;
import com.master.atrium.managementproject.repository.ProjectRepository;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

/**
 * Implementación del servicio de personas
 * @author Rodrigo
 *
 */
@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ProjectPersonRepository projectPersonRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired 
	RoleRepository roleRepository;
	@Autowired
	ProjectRepository projectRepository;
	
		
	/**
	 * Constructor de la clase
	 */
	public PersonServiceImpl() {
		super();
	}

	@Override
	public Person save(final Person person) throws EmailExistsException, UserExistsException {
		Person personFound = personRepository.findById(person.getId());
		
		if(Objects.nonNull(personFound)) {
			update(person, personFound);
		} else {
			insert(person);
		}		
		return findByUser(person.getUser());
	}
	
	private void update(Person person, Person personFound) throws EmailExistsException, UserExistsException {
		List<Project> projectsFound = findAllProjectsByPerson(personFound);
		if (emailExist(person.getEmail()) && !personFound.getEmail().equals(person.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + person.getEmail());
        }
		if (userExist(person.getUser()) && !personFound.getUser().equals(person.getUser())) {
            throw new UserExistsException("There is an account with that username: " + person.getUser());
        }
		person = addRole(person);
		personRepository.update(person);
		Person personFoundSaved = findById(person.getId());
		if(Objects.nonNull(person.getProjects()) && person.getProjects().length > 0) {
			if(Objects.nonNull(projectsFound)){
				updateProjectPersonRelation(person, projectsFound, personFound, personFoundSaved);
			} else {
				insertProjectPersonRelation(person, personFoundSaved);
			}
		} else {
			if(Objects.nonNull(projectsFound)){
				deleteProjectPersonRelation(person, projectsFound, personFoundSaved);
			}
		}
	}
	
	private void insert(Person person) throws EmailExistsException, UserExistsException {
		if (emailExist(person.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + person.getEmail());
        }
		if (userExist(person.getUser())) {
            throw new UserExistsException("There is an account with that username: " + person.getUser());
        }
		person = addRole(person);
		person.setPassword(passwordEncoder.encode(person.getPassword()));		
		personRepository.insert(person);
		Person personSaved = personRepository.findByUser(person.getUser());
		if(Objects.nonNull(person.getProjects()) && person.getProjects().length > 0) {
			insertProjectPersonRelation(person, personSaved);
		}
	}
	
	private Person addRole(Person person) {
		Role role = roleRepository.findByName(person.getRole().getName());
		person.setRole(role);
		return person;
	}
	
	private Person addRoleById(Person person) {
		Role role = roleRepository.findById(person.getRoleId());
		person.setRole(role);
		return person;
	}
	
	private void insertProjectPersonRelation(Person person, Person personFoundSaved) {
		for(int indexProjects = 0; indexProjects < person.getProjects().length; indexProjects++) {
			Project[] projects = mappingArrayIntegerProjectsToArrayProjects(person.getProjects());
			projectPersonRepository.insert(projects[indexProjects], personFoundSaved);
		}
	}
	
	private void deleteProjectPersonRelation(Person person, List<Project> projectsFound, Person personFoundSaved) {
		int indexProjects = 0;
		if(hasProjectPersonToDelete(indexProjects, person)) {
			while(indexProjects < projectsFound.size()) {
				projectPersonRepository.delete(projectsFound.get(indexProjects), personFoundSaved);
				indexProjects++;
			}
		}
	}
	
	private void updateProjectPersonRelation(Person person, List<Project> projectsFound, Person personFound, Person personFoundSaved) {
		int indexProjects = 0;
		while(hasProjectPersonToUpdate(indexProjects, person, projectsFound)) {
			Project[] projects = mappingArrayIntegerProjectsToArrayProjects(person.getProjects());
			projectPersonRepository.update(projects[indexProjects], personFoundSaved, projectsFound.get(indexProjects), personFound);
			indexProjects++;
		}
		if(hasProjectPersonToDelete(indexProjects, person)) {
			while(indexProjects < projectsFound.size()) {
				projectPersonRepository.delete(projectsFound.get(indexProjects), personFoundSaved);
				indexProjects++;
			}
		} else if(hasProjectPersonToInsert(indexProjects, projectsFound)) {
			while(indexProjects < person.getProjects().length) {
				Project[] projects = mappingArrayIntegerProjectsToArrayProjects(person.getProjects());
				projectPersonRepository.insert(projects[indexProjects], personFoundSaved);
				indexProjects++;
			}
		}
	}
	
	private boolean hasProjectPersonToUpdate(int indexProjects, Person person, List<Project> projectsFound) {
		return indexProjects < person.getProjects().length && indexProjects < projectsFound.size();
	}
	
	private boolean hasProjectPersonToDelete(int indexProjects, Person person) {
		return indexProjects >= person.getProjects().length;
	}
	
	private boolean hasProjectPersonToInsert(int indexProjects, List<Project> projectsFound) {
		return indexProjects >= projectsFound.size();
	}
		
	@Override
	public void delete(final Person person) throws RecordReferencedInOtherTablesException {
		List<Project> projects = findAllProjectsByPerson(person);
		if(!projects.isEmpty()) {
			throw new RecordReferencedInOtherTablesException("Record is relationed with at least one project. Delete this reference to delete this record.");
		}
		personRepository.deleteById(person.getId());	
	}

	@Override
	public List<Person> findAll() {
		List<Person> persons = personRepository.findAll();
		for(Person person : persons) {
			person = addRoleById(person);
			person.setProjectList(findAllProjectsByPerson(person));			
		}
		return persons;
	}

	@Override
	public List<Project> findAllProjectsByPerson(Person person) {
		return projectPersonRepository.findAllProjectsByIdPerson(person.getId());
	}

	@Override
	public Person findById(Long id) {
		Person person = personRepository.findById(id);
		if(Objects.nonNull(person)) {
			person = addRole(person);
		}
		return addProjectListToPerson(person);
	}

	@Override
	public Person findByEmail(String email) {
		Person person = personRepository.findByEmail(email);
		if(Objects.nonNull(person)) {
		 person = addRole(person);
		}
		return addProjectListToPerson(person);
	}

	@Override
	public Person findByUser(String user) {
		Person person = personRepository.findByUser(user);
		if(Objects.nonNull(person)) {
			person = addRole(person);
		}
		return addProjectListToPerson(person);
	}
	
	private Project[] mappingArrayIntegerProjectsToArrayProjects(Integer[] projectsInteger) {
		Project[] projects = new Project[projectsInteger.length];
		for (int indexProjects = 0; indexProjects < projectsInteger.length; indexProjects++) {
			projects[indexProjects] = getProjectFromInteger(projectsInteger[indexProjects]);
		}
		return projects;
	}
	
	private Project getProjectFromInteger(Integer projectInteger) {
		return projectRepository.findById(Long.valueOf(projectInteger));
	}
	
	private boolean userExist(String user) {
		return Objects.nonNull(findByUser(user)) ? Boolean.TRUE : Boolean.FALSE; 
	}
	
	private boolean emailExist(String email) {
		return Objects.nonNull(findByEmail(email)) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Person addProjectListToPerson(Person person) {
		if(Objects.nonNull(person)) {
			person.setProjectList(findAllProjectsByPerson(person));
		}
		return person;
	}
	
}
