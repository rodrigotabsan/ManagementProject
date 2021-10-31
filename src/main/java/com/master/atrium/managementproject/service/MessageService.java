package com.master.atrium.managementproject.service;

import java.util.List;

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Task;

/**
 * Servicio de mensaje
 * @author Rodrigo
 *
 */
public interface MessageService {
	/**
	 * Guarda un mensaje
	 * @param message el mensaje
	 * @return
	 */
	public Message save(Message message);
	/**
	 * Elimina un mensaje
	 * @param message el mensaje
	 */
	public void delete(Message message);
	/**
	 * Obtiene toda la lista de mensajes
	 * @return
	 */
	public List<Message> findAll();
	/**
	 * Obtiene un mensaje por su identificador
	 * @param id identificador del mensaje
	 * @return
	 */
	public Message findById(Long id);
	/**
	 * Obtiene un mensaje por su asunto
	 * @param subject asunto del mensaje
	 * @return
	 */
	public Message findBySubject(String subject);
	/**
	 * Obtiene una lista de mensajes dada una tarea como parámetro
	 * @param task tarea
	 * @return
	 */
	public List<Message> findMessagesByTask(Task task);
}
