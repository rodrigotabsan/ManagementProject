package com.master.atrium.managementproject.entity;

/**
 * Clase que representa la tabla de relacion entre proyecto y persona
 * @author Rodrigo
 *
 */
public class ProjectPerson {

	/** Identificador de la persona */
	private Long personId;	
	/** Identificador del proyecto */
	private Long projectId;
	/** Proyecto */
	private Project project;
	/** Persona */
	private Person person;
		
	/** Constructor de la clase */
	public ProjectPerson() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param personId
	 * @param projectId
	 */
	public ProjectPerson(Long personId, Long projectId) {
		super();
		this.personId = personId;
		this.projectId = projectId;
	}
	
	/**
	 * Constructor de la clase
	 * @param project
	 * @param person
	 */
	public ProjectPerson(Project project, Person person) {
		super();
		this.project = project;
		this.person = person;
	}

	/**
	 * Obtiene el identificador de la persona
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}

	/**
	 * Sobrescribe el identificador de la persona
	 * @param personId the personId to set
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	/**
	 * Obtiene el identificador del proyecto
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * Sobrescribe el identificador del proyecto
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * Obtiene el proyecto
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Sobrescribe el proyecto
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Obtiene la persona
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sobrescribe la persona
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	
}
