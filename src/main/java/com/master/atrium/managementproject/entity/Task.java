package com.master.atrium.managementproject.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

/**
 * Clase que describe una tarea
 * @author Rodrigo
 *
 */
public class Task extends Common implements Serializable{
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -4421487172653202549L;
	/** Nombre de una tarea */
	@NotEmpty(message = "name is required.")
	private String name;
	/** Descripción de una tarea */
	@NotEmpty(message = "Description is required.")
	private String description;
	/** Fecha de comienzo de una tarea */
	private Date startDate;
	/** Fecha de finalización de una tarea */
	private Date endDate;
	
	/** Lista de mensajes */
    private List<Message> messages = new ArrayList<>();
	
	/** {@link Project} */
    private Project project;
	
	/** {@link Person} */
    private Person person;
    
    private Long personId;
    
    private Long projectId;
		
	/**
	 * Constructor de la clase
	 */	
	public Task() {
	}

	/**
	 * Constructor de la clase
	 * @param name
	 * @param description
	 * @param startDate
	 * @param endDate
	 */
	public Task(String name, String description, Date startDate, Date endDate) {		
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * Constructor de la clase
	 * @param name
	 * @param description
	 * @param startDate
	 * @param endDate
	 * @param person
	 * @param project
	 */
	public Task(String name, String description, Date startDate, Date endDate, Person person, Project project) {		
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.person = person;
		this.project = project;
	}

	/**
	 * Obtener nombre de la tarea
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sobrescribir nombre de la tarea
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtener descripcion de la tarea
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sobrescribir descripcion de la tarea
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Obtener fecha de comienzo de la tarea
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sobrescribir fecha de comienzo de la tarea
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Obtener fecha de finalización de la tarea
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sobrescribir fecha de finalización de la tarea
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Obtener mensajes
	 * @return the messages
	 */
	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * Sobrescribir mensajes
	 * @param messages the messages to set
	 */
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	/**
	 * Obtener proyecto correspondiente a tarea
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Sobrescribir proyecto correspondiente a tarea
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Obtener persona correspondiente a tarea
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sobrescribir persona correspondiente a tarea
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
}
