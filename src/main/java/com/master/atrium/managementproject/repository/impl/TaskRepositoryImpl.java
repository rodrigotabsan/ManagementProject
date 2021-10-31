package com.master.atrium.managementproject.repository.impl;

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
import com.master.atrium.managementproject.repository.TaskRepository;

@Repository
public class TaskRepositoryImpl implements TaskRepository{
	@Autowired
	private JdbcTemplate template;
	@Autowired
	PersonRepositoryImpl personRepositoryImpl;
	@Autowired
	ProjectRepositoryImpl projectRepositoryImpl;

	private static final String MADRID_ZONE_ID = "Europe/Madrid";
	
	/**
	 * @param template
	 */
	public TaskRepositoryImpl(JdbcTemplate template) {
		super();
		this.template = template;
	}

	@Override
	@Transactional
	public void insert(Task task) {
		LocalDate localDateStartDate = task.getStartDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		LocalDate localEndDateStartDate = task.getEndDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		String query = "INSERT INTO task (description, start_date, name, end_date, person_id, project_id) VALUES (?,?,?,?,?,?);";
		template.update(query, task.getDescription(), localDateStartDate, task.getName(), localEndDateStartDate, task.getPerson().getId(), task.getProject().getId());
	}
	
	@Override
	@Transactional
	public void update(Task task) {
		LocalDate localStartDate = task.getStartDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		Person person = null;
		Project project = null;
		if(Objects.nonNull(task.getPerson())) {
			person = task.getPerson();
		} else {
			person = personRepositoryImpl.findById(task.getPersonId());
		}
		if(Objects.nonNull(task.getProject())) {
			project = task.getProject();
		} else {
			project = projectRepositoryImpl.findById(task.getProjectId());
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
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		String query = "DELETE FROM task WHERE id = ?;";
		template.update(query, id);
	}
	
	@Override
	public List<Task> findAll() {
		String query = "SELECT * FROM task;";
		return template.query(query, new BeanPropertyRowMapper<Task>(Task.class));
	}
	
	@Override
	public Task findById(Long id) {
		String query = "SELECT t.id, t.description, t.name, t.start_date, t.end_date, pj.id as project_id, p.id as person_id FROM task t, person p, project pj WHERE t.id = ? AND t.project_id = pj.id AND t.person_id = p.id;";
		List<Task> tasks = template.query(query, new BeanPropertyRowMapper<Task>(Task.class), id);
		Task task = null;
		if(!tasks.isEmpty()) {
			task = tasks.get(0);
			Person person = personRepositoryImpl.findById(task.getPersonId());
			task.setPerson(person);
			Project project = projectRepositoryImpl.findById(task.getProjectId());
			task.setProject(project);
		}
		return task;
	}
		
	@Override
	public Task findByName(String name) {
		String query = "SELECT t.id, t.description, t.name, t.start_date, t.end_date, pj.id as project_id, p.id as person_id FROM task t, person p, project pj WHERE t.name = ? AND t.project_id = pj.id AND t.person_id = p.id;";
		List<Task> tasks = template.query(query, new BeanPropertyRowMapper<Task>(Task.class), name);
		Task task = null;
		if(!tasks.isEmpty()) {
			task = tasks.get(0);
			Person person = personRepositoryImpl.findById(task.getPersonId());
			task.setPerson(person);
			Project project = projectRepositoryImpl.findById(task.getProjectId());
			task.setProject(project);
		}
		return task;
	}
	
	@Override
	public List<Task> findTasksByProjectId(Long projectId) {
		String query = "SELECT t.id, t.description, t.name, t.start_date, t.end_date, pj.id as project_id, p.id as person_id FROM task t, person p, project pj WHERE t.project_id = ? AND t.project_id = pj.id AND t.person_id = p.id;";
		List<Task> tasks = template.query(query, new BeanPropertyRowMapper<Task>(Task.class), projectId);
		tasks.forEach(task -> {
			Project project = projectRepositoryImpl.findById(task.getProjectId());
			task.setProject(project);
		});
		
		return tasks;
	}
	
	@Override
	public List<Task> findTasksByPersonId(Long personId) {	
		String query = "SELECT t.id, t.description, t.name, t.start_date, t.end_date, pj.id as project_id, p.id as person_id FROM task t, person p, project pj WHERE t.person_id = ? AND t.project_id = pj.id AND t.person_id = p.id;";
		List<Task> tasks = template.query(query, new BeanPropertyRowMapper<Task>(Task.class), personId);
		tasks.forEach(task -> {
			Person person = personRepositoryImpl.findById(task.getPersonId());
			task.setPerson(person);
		});
		
		return tasks;
	}
}
