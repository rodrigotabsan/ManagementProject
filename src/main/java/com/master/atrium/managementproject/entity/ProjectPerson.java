package com.master.atrium.managementproject.entity;

public class ProjectPerson {

	private Long personId;	
	private Long projectId;
	private Project project;
	private Person person;
		
	public ProjectPerson() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param personId
	 * @param projectId
	 */
	public ProjectPerson(Long personId, Long projectId) {
		super();
		this.personId = personId;
		this.projectId = projectId;
	}
	
	/**
	 * @param project
	 * @param person
	 */
	public ProjectPerson(Project project, Person person) {
		super();
		this.project = project;
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

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	
}
