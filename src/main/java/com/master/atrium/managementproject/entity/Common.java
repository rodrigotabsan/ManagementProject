package com.master.atrium.managementproject.entity;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;

/**
 * Clase común que contiene el identificador.
 * @author Rodrigo
 *
 */
public abstract class Common implements Serializable{
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -664121039313279465L;
	@Id
	private Long id;

	/**
	 * Constructor de la clase
	 */
	public Common() {
		
	}
	
	/**
	 * Constructor de la clase
	 * @param id
	 */
	public Common(Long id) {
		super();
		this.id = id;
	}

	/**
	 * Obtiene el id 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sobrescribe el id
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Common))
			return false;
		Common other = (Common) obj;
		return Objects.equals(id, other.id);
	}
}
