package com.master.atrium.managementproject.repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;

@Repository
public class PersonRepository {
	@Autowired
	private JdbcTemplate template;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	RoleRepository roleRepository;
	
	private static final String MADRID_ZONE_ID = "Europe/Madrid";
	/**
	 * @param template
	 */
	public PersonRepository(JdbcTemplate template) {
		super();
		this.template = template;
	}

	@Transactional
	public void insert(Person person) {
		LocalDate localDateStartDate = new Date().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		String query = "insert into person (dni, email, lastname1, lastname2, name, password, start_date, user, role_id) values(?,?,?,?,?,?,?,?,?);";
		template.update(query, 
						person.getDni(),
						person.getEmail(),
						person.getLastname1(),
						person.getLastname2(),
						person.getName(),
						passwordEncoder.encode(person.getPassword()),				
						localDateStartDate,
						person.getUser(),
						person.getRole().getId());
	}
	
	@Transactional
	public void update(Person person) {
		LocalDate localDateStartDate = person.getStartDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		Role role = null;
		if(Objects.nonNull(person.getRole())) {
			role = person.getRole();
		} else {
			role = roleRepository.findById(person.getRoleId());
		}		
		if(Objects.nonNull(person.getEndDate())) {
			LocalDate localDateEndDate = person.getEndDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
					.toLocalDate();
			String query = "UPDATE person SET dni = ?, email = ?, lastname1 = ?, lastname2 = ?, name = ? , start_date = ?, user = ?, role_id = ?, end_date = ? WHERE id = ?;";
			template.update(query, 
							person.getDni(),
							person.getEmail(),
							person.getLastname1(),
							person.getLastname2(),
							person.getName(),								
							localDateStartDate,
							person.getUser(),
							role.getId(),
							localDateEndDate,
							person.getId());
		} else {
			String query = "UPDATE person SET dni = ?, email = ?, lastname1 = ?, lastname2 = ?, name = ? , start_date = ?, user = ?, role_id = ? WHERE id = ?;";
			template.update(query, 
							person.getDni(),
							person.getEmail(),
							person.getLastname1(),
							person.getLastname2(),
							person.getName(),			
							localDateStartDate,
							person.getUser(),
							role.getId(),
							person.getId());
		}
	}
	
	@Transactional
	public void deleteById(Long id) {
		String query = "DELETE FROM person WHERE id = ?;";
		template.update(query, id);
	}
	
	public List<Person> findAll() {
		String query = "SELECT * FROM person;";
		return template.query(query, new BeanPropertyRowMapper<Person>(Person.class));
	}
	
	public Person findById(Long id) {
		String query = "SELECT p.id, p.dni, p.email, p.end_date, p.start_date, p.lastname1, p.lastname2, p.name, p.password, p.user, r.id as role_id FROM person p, role r WHERE p.id = ? AND p.role_id = r.id;";
		List<Person> persons = template.query(query, new BeanPropertyRowMapper<Person>(Person.class), id);
		Person person = null;
		if(Objects.nonNull(persons) && !persons.isEmpty()) {
			person = persons.get(0);
			Role role = roleRepository.findById(person.getRoleId());
			person.setRole(role);
		}
		return person;
	}
		
	public Person findByEmail(String email) {
		String query = "SELECT p.id, p.dni, p.email, p.end_date, p.start_date, p.lastname1, p.lastname2, p.name, p.password, p.user, r.id as role_id FROM person p, role r WHERE p.email = ? AND p.role_id = r.id;";
		List<Person> persons = template.query(query, new BeanPropertyRowMapper<Person>(Person.class), email);
		Person person = null;
		if(Objects.nonNull(persons) && !persons.isEmpty()) {
			person = persons.get(0);
			Role role = roleRepository.findById(person.getRoleId());
			person.setRole(role);
		}
		return person;
	}
	
	public Person findByUser(String user) {
		String query = "SELECT p.id, p.dni, p.email, p.end_date, p.start_date, p.lastname1, p.lastname2, p.name, p.password, p.user, r.id as role_id FROM person p, role r WHERE p.user = ? AND p.role_id = r.id;";
		List<Person> persons = template.query(query, new BeanPropertyRowMapper<Person>(Person.class), user);
		Person person = null;
		if(Objects.nonNull(persons) && !persons.isEmpty()) {
			person = persons.get(0);
			Role role = roleRepository.findById(person.getRoleId());
			person.setRole(role);
		}
		return person;
	}
	
}