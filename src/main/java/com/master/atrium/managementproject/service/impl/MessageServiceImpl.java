package com.master.atrium.managementproject.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Task;
import com.master.atrium.managementproject.repository.MessageRepository;
import com.master.atrium.managementproject.repository.TaskRepository;
import com.master.atrium.managementproject.service.MessageService;

/**
 * Implementación del servicio de mensajes
 * @author Rodrigo
 *
 */
@Service
public class MessageServiceImpl implements MessageService{

	/**
	 * Inyección de repositorio de mensajes
	 */
	@Autowired
	MessageRepository messageRepository;
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	/**
	 * Inyección de repositorio de tareas
	 */
	@Autowired
	TaskRepository taskRepository;
	
	/**
	 * Constructor de la clase
	 */
	public MessageServiceImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message save(Message message) {
		Message messageFound = messageRepository.findBySubject(message.getSubject());
		if(isUpdate(messageFound, messageFound)) {
			message.setDate(new Date());
			messageRepository.update(message);
			LOG.info("Se ha actualizado un mensaje");
		} else {
			message.setDate(new Date());
			messageRepository.insert(message);
			LOG.info("Se ha insertado un mensaje");
		}
		return messageRepository.findBySubject(message.getSubject());
	}
	
	/**
	 * Verifica si el mensaje está actualizado
	 * @param message mensaje
	 * @param messageFound mensaje encontrado
	 * @return
	 */
	private boolean isUpdate(Message message, Message messageFound) {
		return Objects.nonNull(messageFound) 
				&& messageFound.getSubject().equals(message.getSubject())
				&& messageFound.getId().equals(message.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Message message) {
		if(Objects.nonNull(message) && Objects.nonNull(message.getId())) {
			messageRepository.deleteById(message.getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Message> findAll() {
		return messageRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message findById(Long id) {
		return messageRepository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message findBySubject(String subject) {
		return messageRepository.findBySubject(subject);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Message> findMessagesByTask(Task task) {
		List<Message> messages = new ArrayList<>();
		if(taskIsNotNull(task)) {
			messages = messageRepository.findMessagesByTaskId(task.getId()); 
			messages.forEach(message -> message.setTask(task));			
		}
		return messages;
	}
	
	/**
	 * Verifica que una tarea no sea nula
	 * @param task la tarea
	 * @return
	 */
	private boolean taskIsNotNull(Task task) {
		return Objects.nonNull(task) && Objects.nonNull(task.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAllByTask(Task task) {
		List<Message> messages = findMessagesByTask(task);
    	for(Message message : messages) {
    		delete(message);
    	}
	}

}
