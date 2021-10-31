package com.master.atrium.managementproject.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

/**
 * Clase que proporciona un rol a un usuario
 * @author Rodrigo
 *
 */
public class Role extends Common implements Serializable{

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -7996814875776204283L;

	/**
	 * Nombre del rol
	 */
	@NotEmpty(message = "Name is required.")
	private String name;
	
	/** Lista de {@link Person} */
	private List<Person> persons = new ArrayList<>();
	
	/**
	 * Constructor de la clase
	 */
	public Role() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param id
	 * @param name
	 */
	public Role(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el nombre del rol
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sobrescribe el nombre del rol
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene una lista de personas
	 * @return the persons
	 */
	public List<Person> getPersons() {
		return persons;
	}

	/**
	 * Sobrescribe una lista de personas
	 * @param persons2 the persons to set
	 */
	public void setPersons(List<Person> persons2) {
		this.persons = persons2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name, persons);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Role))
			return false;
		Role other = (Role) obj;
		return Objects.equals(name, other.name) && Objects.equals(persons, other.persons);
	}
	
}
