package com.master.atrium.managementproject.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.master.atrium.managementproject.validator.PasswordValidator;

/**
 * Clase que recoje la información del usuario
 * @author Rodrigo
 *
 */
@PasswordValidator
public class Person extends Common implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1451013088486247992L;
	/** DNI del usuario */
	@NotEmpty(message = "DNI is required.")
	private String dni;
	/** Nombre de usuario */
	@NotEmpty(message = "Username is required.")
	private String user;
	/** Nombre de la persona */
	@NotEmpty(message = "Name is required.")
	private String name;
	/** Primer apellido de la persona */
	@NotEmpty(message = "Lastname1 is required.")
	private String lastname1;
	/** Segundo apellido de la persona */
	@NotEmpty(message = "Lastname2 is required.")
	private String lastname2;
	/** Email del usuario */
	@NotEmpty(message = "Email is required.")
	private String email;
	/** Contraseña del usuario */	
	@NotEmpty(message = "Password is required.")
	private String password;
	/** Contraseña de confirmación del usuario */
	private String passwordConfirmation;
	/** Fecha de alta del usuario */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	/** Fecha de baja del usuario */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	
	private Integer[] projects;
	
    private Set<Task> tasks = new HashSet<>();
		
    private List<Project> projectList;
	private Role role;
	
	private Long roleId;
	
	/**
	 * Constructor de la clase
	 */
	public Person() {
		
	}

	/**
	 * Constructor de la clase
	 * @param dni
	 * @param user
	 * @param name
	 * @param lastname1
	 * @param lastname2
	 * @param email
	 * @param password
	 * @param idRol
	 * @param startDate
	 * @param endDate
	 */
	public Person(String dni, String user, String name, String lastname1, String lastname2, String email,
			String password, Role role, Date startDate, Date endDate) {
		this.dni = dni;
		this.user = user;
		this.name = name;
		this.lastname1 = lastname1;
		this.lastname2 = lastname2;
		this.email = email;
		this.password = password;
		this.role = role;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Obtiene el DNI
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Sobrescribe el DNI
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Obtiene el nombre de usuario
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sobrescribe el nombre de usuario
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Obtiene el nombre de la persona
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sobrescribe el nombre de la persona
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el primer apellido de la persona
	 * @return the lastname1
	 */
	public String getLastname1() {
		return lastname1;
	}

	/**
	 * Sobrescribe el primer apellido de la persona
	 * @param lastname1 the lastname1 to set
	 */
	public void setLastname1(String lastname1) {
		this.lastname1 = lastname1;
	}

	/**
	 * Obtiene el segundo apellido de la persona
	 * @return the lastname2
	 */
	public String getLastname2() {
		return lastname2;
	}

	/**
	 * Sobrescribe el segundo apellido de la persona
	 * @param lastname2 the lastname2 to set
	 */
	public void setLastname2(String lastname2) {
		this.lastname2 = lastname2;
	}

	/**
	 * Obtiene el email del usuario
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sobrescribe el email del usuario
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene la contraseña del usuario
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sobrescribe la contraseña del usuario 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtiene el rol del usuario
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Sobrescribe el rol del usuario
	 * @param idRole the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Obtiene la fecha de alta del usuario
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sobrescribe la fecha de alta del usuario
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Obtiene la fecha de baja del usuario
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sobrescribe la fecha de baja del usuario
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Obtiene las tareas de esta persona
	 * @return the tasks
	 */
	public Set<Task> getTasks() {
		return tasks;
	}

	/**
	 * Sobrescribe las tareas de esta persona
	 * @param tasks the tasks to set
	 */
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Obtener lista de proyectos
	 * @return the projectList
	 */
	public List<Project> getProjectList() {
		return projectList;
	}

	/**
	 * Sobrescribe la lista de proyectos
	 * @param projectList the projectList to set
	 */
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	/**
	 * Obtiene la confirmación de la contraseña
	 * @return the passwordConfirmation
	 */
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	/**
	 * Sobrescribe la confirmación de la contraseña
	 * @param passwordConfirmation the passwordConfirmation to set
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	/**
	 * @return the projects
	 */
	public Integer[] getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(Integer[] projects) {
		this.projects = projects;
	}
	
	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(projects);
		result = prime * result + Objects.hash(dni, email, endDate, lastname1, lastname2, name, password,
				passwordConfirmation, projectList, role, startDate, tasks, user);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Person))
			return false;
		Person other = (Person) obj;
		return Objects.equals(dni, other.dni) && Objects.equals(email, other.email)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(lastname1, other.lastname1)
				&& Objects.equals(lastname2, other.lastname2) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password)
				&& Objects.equals(passwordConfirmation, other.passwordConfirmation)
				&& Objects.equals(projectList, other.projectList) && Arrays.equals(projects, other.projects)
				&& Objects.equals(role, other.role) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(tasks, other.tasks) && Objects.equals(user, other.user);
	}
		
}
