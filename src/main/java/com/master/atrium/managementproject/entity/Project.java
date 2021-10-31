package com.master.atrium.managementproject.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase que obtiene la información de los proyectos.
 * @author Rodrigo
 *
 */
public class Project extends Common implements Serializable{
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 5111111789925051173L;
	/** Nombre del proyecto */
	@NotEmpty(message = "Name is required.")
	private String name;
	/** Descripcion del proyecto */
	@NotEmpty(message = "Description is required.")
	private String description;
	/** Fecha de comienzo del proyecto */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	/** Fecha de finalización del proyecto */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	/** Array con los identificadores de las personas seleccionadas en el formulario */
	private Integer[] persons; 
	
	/** Lista de {@link Task} */
	private List<Task> tasks;
	
	/**
	 * Lista de personas
	 */
	private List<Person> personList;
	
	/**
	 * Constructor de la clase
	 */
	public Project() {
		
	}

	/**
	 * Constructor de la clase
	 * @param name Nombre del proyecto
	 * @param description Descripción del proyecto
	 * @param startDate Fecha comienzo del proyecto
	 * @param endDate Fecha fin del proyecto
	 */
	public Project(String name, String description, Date startDate, Date endDate) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Obtener nombre del proyecto
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sobrescribir nombre del proyecto
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtener descripción del proyecto
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sobrescribir la descripción del proyecto
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Obtener fecha comienzo del proyecto
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sobrescribir fecha comienzo del proyecto
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Obtener fecha finalizacion del proyecto
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sobrescribir fecha finalizacion del proyecto
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Obtiene la lista de tareas de este proyecto
	 * @return the tasks
	 */
	public List<Task> getTasks() {
		return tasks;
	}

	/**
	 * Sobrescribe la lista de tareas de este proyecto
	 * @param tasks the tasks to set
	 */
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Obtiene la lista de personas 
	 * @return the person
	 */
	public List<Person> getPersonList() {
		return personList;
	}

	/**
	 * Sobrescribe la lista de personas
	 * @param person the person to set
	 */
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

	/**
	 * Obtiene el array de personas
	 * @return the persons
	 */
	public Integer[] getPersons() {
		return persons;
	}

	/**
	 * Sobrescribe el array de personas
	 * @param persons the persons to set
	 */
	public void setPersons(Integer[] persons) {
		this.persons = persons;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(persons);
		result = prime * result + Objects.hash(description, endDate, name, personList, startDate, tasks);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Project))
			return false;
		Project other = (Project) obj;
		return Objects.equals(description, other.description) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(name, other.name) && Objects.equals(personList, other.personList)
				&& Arrays.equals(persons, other.persons) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(tasks, other.tasks);
	}
	
}
