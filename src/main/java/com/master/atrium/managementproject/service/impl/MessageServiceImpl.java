package com.master.atrium.managementproject.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
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

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	MessageRepository messageRepository;
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	@Autowired
	TaskRepository taskRepository;
	
	/**
	 * Constructor de la clase
	 */
	public MessageServiceImpl() {
		super();
	}

	@Override
	public Message save(Message message) {
		Message messageFound = messageRepository.findBySubject(message.getSubject());
		if(Objects.nonNull(messageFound) 
				&& messageFound.getSubject().equals(message.getSubject())
				&& messageFound.getId().equals(message.getId())) {
			messageRepository.update(message);
			LOG.info("Se ha actualizado un mensaje");
		} else {
			messageRepository.insert(message);
			LOG.info("Se ha insertado un mensaje");
		}
		return messageRepository.findBySubject(message.getSubject());
	}

	@Override
	public void delete(Message message) {
		if(Objects.nonNull(message) && Objects.nonNull(message.getId())) {
			messageRepository.deleteById(message.getId());
		}
	}

	@Override
	public List<Message> findAll() {
		return messageRepository.findAll();
	}

	@Override
	public Message findById(Long id) {
		return messageRepository.findById(id);
	}

	@Override
	public Message findBySubject(String subject) {
		return messageRepository.findBySubject(subject);
	}

	@Override
	public List<Message> findMessagesByTask(Task task) {
		List<Message> messages = new ArrayList<>();
		if(Objects.nonNull(task) && Objects.nonNull(task.getId())) {
			messages = messageRepository.findMessagesByTaskId(task.getId()); 
			messages.forEach(message -> message.setTask(task));			
		}
		return messages;
	}

}
