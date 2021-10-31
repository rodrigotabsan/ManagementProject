package com.master.atrium.managementproject.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

/**
 * Clase que contiene el mensaje de un usuario
 * @author Rodrigo
 *
 */
public class Message extends Common implements Serializable{
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5312928343136092630L;
	/** Cabecera del mensaje */
	@NotEmpty(message = "Subject is required.")
	private String subject;
	/** Cuerpo del mensaje */
	@NotEmpty(message = "Body is required.")
	private String body;
	/** Fecha del mensaje */
	@NotEmpty(message = "Date is required.")
	private Date date;

	/**
	 * Tarea a la que pertenece el mensaje
	 */
    private Task task;
    
    /**
     * Identificador de la tarea a la que pertenece el mensaje
     */
    private Long taskId;
	
	/**
	 * Constructor de la clase
	 */
	public Message() {

	}

	/**
	 * Constructor de la clase
	 * @param subject
	 * @param body
	 * @param idTask
	 * @param date
	 */
	public Message(String subject, String body, Date date) {
		this.subject = subject;
		this.body = body;
		this.date = date;
	}
	
	/**
	 * Constructor de la clase
	 * @param subject
	 * @param body
	 * @param idTask
	 * @param date
	 */
	public Message(String subject, String body, Date date, Task task) {
		this.subject = subject;
		this.body = body;
		this.date = date;
		this.task = task;
	}

	/**
	 * Obtiene la cabecera del mensaje 
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sobrescribe la cabecera del mensaje 
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Obtiene el cuerpo del mensaje
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sobrescribe el cuerpo del mensaje
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Obtiene la fecha del mensaje
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sobrescribe la fecha del mensaje
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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
	 * Obtiene el identificador de la tarea
	 * @return the taskId
	 */
	public Long getTaskId() {
		return taskId;
	}

	/**
	 * Sobrescribe el identificador de la tarea
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(body, date, subject, task, taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Message))
			return false;
		Message other = (Message) obj;
		return Objects.equals(body, other.body) && Objects.equals(date, other.date)
				&& Objects.equals(subject, other.subject) && Objects.equals(task, other.task)
				&& Objects.equals(taskId, other.taskId);
	}
	
	
		
}
