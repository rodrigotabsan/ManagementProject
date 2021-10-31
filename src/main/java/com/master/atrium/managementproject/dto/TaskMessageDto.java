package com.master.atrium.managementproject.dto;

import com.master.atrium.managementproject.entity.Message;
import com.master.atrium.managementproject.entity.Task;

/**
 * Clase que obtiene del formulario las tareas y los mensajes
 * @author Rodrigo
 *
 */
public class TaskMessageDto {

	/** Tarea */
	Task task;
	/** Mensaje */
	Message message;
	
	/**
	 * Constructor de la clase
	 */
	public TaskMessageDto() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param task
	 * @param message
	 */
	public TaskMessageDto(Task task, Message message) {
		this.task = task;
		this.message = message;
	}

	/**
	 * Obtiene la tarea
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Sobrescribe la tarea
	 * @param task the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * Obtiene el mensaje
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * Sobrescribe el mensaje
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

}
