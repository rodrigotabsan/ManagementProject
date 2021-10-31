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

import com.master.atrium.managementproject.entity.Project;
import com.master.atrium.managementproject.repository.ProjectRepository;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository{
	@Autowired
	private JdbcTemplate template;

	private static final String MADRID_ZONE_ID = "Europe/Madrid";
	/**
	 * @param template
	 */
	public ProjectRepositoryImpl(JdbcTemplate template) {
		super();
		this.template = template;
	}
	
	@Override
	@Transactional
	public void insert(Project project) {
		LocalDate localDateStartDate = project.getStartDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		LocalDate localDateEndDate = project.getEndDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();
		String query = "INSERT INTO project (description, start_date, name, end_date) VALUES (?,?,?,?);";
		template.update(query, 
				project.getDescription(),
				localDateStartDate,
				project.getName(),
				localDateEndDate);
	}
	@Override
	@Transactional
	public void update(Project project) {
		LocalDate localDateStartDate = project.getStartDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
				.toLocalDate();				
		if(Objects.nonNull(project.getEndDate())) {
			LocalDate localDateEndDate = project.getEndDate().toInstant().atZone(ZoneId.of(MADRID_ZONE_ID))
					.toLocalDate();
			String query = "UPDATE project SET description = ?, start_date = ?, name = ?, end_date = ? WHERE id = ?;";
			template.update(query, 
							project.getDescription(),
							localDateStartDate,
							project.getName(), 
							localDateEndDate, 
							project.getId());
		} else {
			String query = "UPDATE project SET description = ?, start_date = ?, name = ? WHERE id = ?;";
			template.update(query, 
							project.getDescription(),
							localDateStartDate,
							project.getName(), 
							project.getId());
		}
	}
	@Override
	@Transactional
	public void deleteById(Long id) {
		String query = "DELETE FROM project WHERE id = ?;";
		template.update(query, id);
	}
	@Override
	public List<Project> findAll() {
		String query = "SELECT * FROM project;";
		return template.query(query, new BeanPropertyRowMapper<Project>(Project.class));
	}
	@Override
	public Project findById(Long id) {
		String query = "SELECT * FROM project WHERE id = ?;";
		List<Project> projects = template.query(query, new BeanPropertyRowMapper<Project>(Project.class), id);
		Project project = null;
		if(!projects.isEmpty()) {
			project = projects.get(0);
		}
		return project;
	}
	@Override
	public Project findByName(String name) {
		String query = "SELECT * FROM project WHERE name = ?;";
		List<Project> projects = template.query(query, new BeanPropertyRowMapper<Project>(Project.class), name);
		Project project = null;
		if(!projects.isEmpty()) {
			project = projects.get(0);
		}
		return project;
	}
}
