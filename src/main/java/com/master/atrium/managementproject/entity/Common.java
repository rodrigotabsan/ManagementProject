package com.master.atrium.managementproject.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

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
}
