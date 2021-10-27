package com.master.atrium.managementproject.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.ProjectPersonRepository;
import com.master.atrium.managementproject.service.PersonService;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.RecordReferencedInOtherTablesException;
import com.master.atrium.managementproject.validator.UserExistsException;

/**
 * Implementación del servicio de personas
 * @author Rodrigo
 *
 */
public class PersonServiceImpl implements PersonService {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ProjectPersonRepository projectPersonRepository;
	@Autowired
	PersonRepository personRepository;
		
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
			List<Project> projectsFound = findAllProjectsByPerson(personFound);
			if (emailExist(person.getEmail()) && !personFound.getEmail().equals(person.getEmail())) {
	            throw new EmailExistsException("There is an account with that email address: " + person.getEmail());
	        }
			if (userExist(person.getUser()) && !personFound.getUser().equals(person.getUser())) {
	            throw new UserExistsException("There is an account with that username: " + person.getUser());
	        }
			personRepository.update(person);
			Person personFoundSaved = personRepository.findById(person.getId());
			if(Objects.nonNull(person.getProjects()) && person.getProjects().length > 0) {
				if(Objects.nonNull(projectsFound)){
					int indexProjects = 0;
					while(indexProjects < person.getProjects().length && indexProjects < projectsFound.size()) {
						projectPersonRepository.update(person.getProjects()[indexProjects], personFoundSaved, projectsFound.get(indexProjects), personFound);
						indexProjects++;
					}
					if(indexProjects >= person.getProjects().length) {
						while(indexProjects < projectsFound.size()) {
							projectPersonRepository.delete(projectsFound.get(indexProjects), personFoundSaved);
							indexProjects++;
						}
					} else if(indexProjects >= projectsFound.size()) {
						while(indexProjects < person.getProjects().length) {
							projectPersonRepository.insert(person.getProjects()[indexProjects], personFoundSaved);
							indexProjects++;
						}
					}
				} else {
					for(int indexProjects = 0; indexProjects < person.getProjects().length; indexProjects++) {						
						projectPersonRepository.insert(person.getProjects()[indexProjects], personFoundSaved);
					}
				}
			}
		} else {
			if (emailExist(person.getEmail())) {
	            throw new EmailExistsException("There is an account with that email address: " + person.getEmail());
	        }
			if (userExist(person.getUser())) {
	            throw new UserExistsException("There is an account with that username: " + person.getUser());
	        }
			person.setPassword(passwordEncoder.encode(person.getPassword()));
			personRepository.insert(person);
			Person personSaved = personRepository.findByUser(person.getUser());
			if(Objects.nonNull(person.getProjects()) && person.getProjects().length > 0) {
				for(int indexProjects = 0; indexProjects < person.getProjects().length; indexProjects++) {
					projectPersonRepository.insert(person.getProjects()[indexProjects], personSaved);
				}
			}
		}
		
		return findByUser(person.getUser());
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
		return addProjectListToPerson(person);
	}

	@Override
	public Person findByEmail(String email) {
		Person person = personRepository.findByEmail(email);
		return addProjectListToPerson(person);
	}

	@Override
	public Person findByUser(String user) {
		Person person = personRepository.findByUser(user);
		return addProjectListToPerson(person);
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
