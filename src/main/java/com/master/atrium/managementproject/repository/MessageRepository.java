package com.master.atrium.managementproject.repository;

import java.util.List;

import com.master.atrium.managementproject.entity.Message;

/**
 * Interface de repositorio de mensajes con las consultas a base de datos
 * @author Rodrigo
 *
 */
public interface MessageRepository {
	
	/**
	 * Crea un nuevo mensaje
	 * @param message El mensaje
	 */
	public void insert(Message message);
	
	/**
	 * Actualiza un mensaje
	 * @param message El mensaje
	 */
	public void update(Message message);
	
	/**
	 * Elimina un mensaje usando como parámetro su identificador
	 * @param id identificador del mensaje
	 */
	public void deleteById(Long id);
	
	/**
	 * Obtiene una lista de todos los mensajes
	 * @return
	 */
	public List<Message> findAll();
	
	/**
	 * Encuentra un mensaje usando su identificador como parámetro
	 * @param id identificador del mensaje
	 * @return
	 */
	public Message findById(Long id);
	
	/**
	 * Encuentra un asunto del mensaje utilizando como parámetro el asunto
	 * @param subject asunto del mensaje
	 * @return
	 */
	public Message findBySubject(String subject);
	
	/**
	 * Encuentra una lista de mensajes utilizando como parámetro el identificador de la tarea
	 * @param id identificador de la tarea
	 * @return
	 */
	public List<Message> findMessagesByTaskId(Long id);
}
