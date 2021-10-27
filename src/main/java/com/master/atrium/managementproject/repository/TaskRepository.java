package com.master.atrium.managementproject.repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.entity.Task;

@Repository
public class TaskRepository {
	@Autowired
	private JdbcTemplate template;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	ProjectRepository projectRepository;

	private static final String MADRID_ZONE_ID = "Europe/Madrid";
	
	/**
	 * @param template
	 */
	public TaskRepository(JdbcTemplate template) {
		super();
		this.template = template;
	}

	@Transactional
	public void insert(Task task) {
		LocalDate localDateStartDate = task.getStartDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		LocalDate localEndDateStartDate = task.getEndDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		String query = "INSERT INTO task (description, start_date, name, end_date, person_id, project_id) VALUES (?,?,?,?,?,?);";
		template.update(query, task.getDescription(), localDateStartDate, task.getName(), localEndDateStartDate, task.getPerson().getId(), task.getProject().getId());
	}
	
	@Transactional
	public void update(Task task) {
		LocalDate localStartDate = task.getStartDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		Person person = null;
		Project project = null;
		if(Objects.nonNull(task.getPerson())) {
			person = task.getPerson();
		} else {
			person = personRepository.findById(task.getPersonId());
		}
		if(Objects.nonNull(task.getProject())) {
			project = task.getProject();
		} else {
			project = projectRepository.findById(task.getProjectId());
		}
		if(Objects.nonNull(task.getEndDate())) {
			LocalDate localEndDate = task.getEndDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
					.toLocalDate();
			String query = "UPDATE task SET description = ?, name = ?, start_date = ?, person_id = ?, project_id = ?, end_date = ? WHERE id = ?;";
			template.update(query, 
							task.getDescription(),
							task.getName(),
							localStartDate,
							person.getId(),
							project.getId(),
							localEndDate,
							task.getId());
		} else {
			String query = "UPDATE task SET description = ?, name = ?, start_date = ?, person_id = ?, project_id = ? WHERE id = ?;";
			template.update(query, 
							task.getDescription(),
							task.getName(),
							localStartDate,
							person.getId(),
							project.getId(),
							task.getId());
		}
	}
	
	@Transactional
	public void deleteById(Long id) {
		String query = "DELETE FROM task WHERE id = ?;";
		template.update(query, id);
	}
	
	public List<Task> findAll() {
		String query = "SELECT * FROM task;";
		return template.query(query, new BeanPropertyRowMapper<Task>(Task.class));
	}
	
	public Task findById(Long id) {
		String query = "SELECT t.id, t.description, t.name, t.start_date, t.end_date, pj.id as project_id, p.id as person_id FROM task t, person p, project pj WHERE t.id = ? AND t.project_id = pj.id AND t.person_id = p.id;";
		List<Task> tasks = template.query(query, new BeanPropertyRowMapper<Task>(Task.class), id);
		Task task = null;
		if(!tasks.isEmpty()) {
			task = tasks.get(0);
			Person person = personRepository.findById(task.getPersonId());
			task.setPerson(person);
			Project project = projectRepository.findById(task.getProjectId());
			task.setProject(project);
		}
		return task;
	}
		
	public Task findByName(String name) {
		String query = "SELECT t.id, t.description, t.name, t.start_date, t.end_date, pj.id as project_id, p.id as person_id FROM task t, person p, project pj WHERE t.name = ? AND t.project_id = pj.id AND t.person_id = p.id;";
		List<Task> tasks = template.query(query, new BeanPropertyRowMapper<Task>(Task.class), name);
		Task task = null;
		if(!tasks.isEmpty()) {
			task = tasks.get(0);
			Person person = personRepository.findById(task.getPersonId());
			task.setPerson(person);
			Project project = projectRepository.findById(task.getProjectId());
			task.setProject(project);
		}
		return task;
	}
	
	public List<Task> findTasksByProjectId(Long projectId) {
		String query = "SELECT t.id, t.description, t.name, t.start_date, t.end_date, pj.id as project_id, p.id as person_id FROM task t, person p, project pj WHERE t.project_id = ? AND t.project_id = pj.id AND t.person_id = p.id;";
		List<Task> tasks = template.query(query, new BeanPropertyRowMapper<Task>(Task.class), projectId);
		tasks.forEach(task -> {
			Project project = projectRepository.findById(task.getProjectId());
			task.setProject(project);
		});
		
		return tasks;
	}
	
	public List<Task> findTasksByPersonId(Long personId) {	
		String query = "SELECT t.id, t.description, t.name, t.start_date, t.end_date, pj.id as project_id, p.id as person_id FROM task t, person p, project pj WHERE t.person_id = ? AND t.project_id = pj.id AND t.person_id = p.id;";
		List<Task> tasks = template.query(query, new BeanPropertyRowMapper<Task>(Task.class), personId);
		tasks.forEach(task -> {
			Person person = personRepository.findById(task.getPersonId());
			task.setPerson(person);
		});
		
		return tasks;
	}
}
