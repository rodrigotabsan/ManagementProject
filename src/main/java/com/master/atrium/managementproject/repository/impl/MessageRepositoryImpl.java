package com.master.atrium.managementproject.repository.impl;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Task;
import com.master.atrium.managementproject.repository.MessageRepository;
import com.master.atrium.managementproject.repository.TaskRepository;

/**
 * Implementación del repositorio de mensajes
 * @author Rodrigo
 *
 */
@Repository
public class MessageRepositoryImpl implements MessageRepository{
	/** Log de la clase */
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	/**
	 * Inyección de {@link JdbcTemplate}
	 */
	@Autowired
	private JdbcTemplate template;

	/**
	 * Inyección de {@link TaskRepository}
	 */
	@Autowired
	TaskRepository taskRepository;
	
	/** Constante query*/
	private static final String QUERY = "Query:{";
	/** Constante PARAMS*/
	private static final String PARAMS = "} Params:{";
	/** Constante END_CURLY_BRACKET*/
	private static final String END_CURLY_BRACKET = "}";
	/**
	 * Constructor de la clase
	 * @param template
	 */
	public MessageRepositoryImpl(JdbcTemplate template) {
		super();
		this.template = template;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void insert(Message message) {
		LocalDate localDate = new Date().toInstant().atZone(ZoneId.of("Europe/Berlin"))
				.toLocalDate();
		String query = "INSERT INTO message (body, date, subject, task_id) VALUES (?,?,?,?);";
		LOG.info(String.join(" ", QUERY, query, PARAMS,message.getBody()+",",localDate+",",message.getSubject()+",",String.valueOf(message.getTask().getId()),END_CURLY_BRACKET));
		template.update(query, 
						message.getBody(),
						localDate,
						message.getSubject(),
						message.getTask().getId());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void update(Message message) {
		LocalDate localDate = message.getDate().toInstant().atZone(ZoneId.of("Europe/Berlin"))
				.toLocalDate();
		Task task = null;
		if(Objects.nonNull(message.getTask())) {
			task = message.getTask();
		} else {
			task = taskRepository.findById(message.getTaskId());
		}
		String query = "UPDATE message SET body = ?, date = ?, subject = ?, task_id = ? WHERE id = ?;";
		LOG.info(String.join(" ", QUERY, query, PARAMS,message.getBody()+",",localDate+",",message.getSubject()+",",String.valueOf(message.getTask().getId()),END_CURLY_BRACKET));
		template.update(query, 
						message.getBody(),
						localDate,
						message.getSubject(),
						task.getId(),
						message.getId());		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		String query = "DELETE FROM message WHERE id = ?;";
		LOG.info(String.join(" ", QUERY, query, PARAMS,String.valueOf(id)),END_CURLY_BRACKET);
		template.update(query, id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Message> findAll() {
		String query = "SELECT * FROM message;";
		LOG.info(String.join(" ", QUERY, query,END_CURLY_BRACKET));
		return template.query(query, new BeanPropertyRowMapper<Message>(Message.class));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message findById(Long id) {
		String query = "SELECT m.id, m.body, m.subject, m.date, t.id as task_id FROM message m, task t WHERE m.id = ? AND m.task_id = t.id;";
		LOG.info(String.join(" ", QUERY, query, PARAMS,String.valueOf(id),END_CURLY_BRACKET));
		List<Message> messages = template.query(query, new BeanPropertyRowMapper<Message>(Message.class), id);
		Message message = null;
		if(!messages.isEmpty()) {
			message = messages.get(0);
			Task task = taskRepository.findById(message.getTaskId());
			message.setTask(task);
		}
		return message;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message findBySubject(String subject) {
		String query = "SELECT m.id, m.body, m.subject, m.date, t.id as task_id FROM message m, task t WHERE m.subject = ? AND m.task_id = t.id;";
		LOG.info(String.join(" ", QUERY, query,END_CURLY_BRACKET, "Params:{",subject,END_CURLY_BRACKET));
		List<Message> messages = template.query(query, new BeanPropertyRowMapper<Message>(Message.class), subject);
		Message message = null;
		if(!messages.isEmpty()) {
			message = messages.get(0);
			Task task = taskRepository.findById(message.getTaskId());
			message.setTask(task);
		}
		return message;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Message> findMessagesByTaskId(Long id) {
		String query = "SELECT m.id, m.body, m.subject, m.date, t.id as task_id FROM message m, task t WHERE m.task_id = ? AND m.task_id = t.id;";
		LOG.info(String.join(" ", QUERY, query,END_CURLY_BRACKET, "Params:{",id.toString(),END_CURLY_BRACKET));
		return template.query(query, new BeanPropertyRowMapper<Message>(Message.class), id);
	}
}
