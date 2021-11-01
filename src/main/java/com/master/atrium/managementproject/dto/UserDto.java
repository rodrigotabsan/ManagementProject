package com.master.atrium.managementproject.dto;

import java.util.Objects;

/**
 * Clase que contiene el usuario para realizar la restauración de la contraseña
 * @author Rodrigo
 *
 */
public class UserDto {
	
	/**
	 * El usuario a restaurar contraseña
	 */
	String user;
	
	/**
	 * Constructor de la clase
	 */
	public UserDto() {
		super();
	}
	
	/**
	 * Constructor de la clase
	 * @param user
	 */
	public UserDto(String user) {
		super();
		this.user = user;
	}

	/**
	 * Obtiene el usuario
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sobrescribe el usuario
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UserDto))
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(user, other.user);
	}
}
