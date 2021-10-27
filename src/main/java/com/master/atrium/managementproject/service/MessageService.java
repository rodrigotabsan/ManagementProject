package com.master.atrium.managementproject.service;

import java.util.List;

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Task;

public interface MessageService {
	public Message save(Message message);
	public void delete(Message message);
	public List<Message> findAll();
	public Message findById(Long id);
	public Message findBySubject(String subject);
	public List<Message> findMessagesByTask(Task task);
}
