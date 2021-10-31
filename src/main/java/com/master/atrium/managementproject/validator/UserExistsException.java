package com.master.atrium.managementproject.validator;

/**
 * Clase que lanza la excepción de usuario existente
 * @author Rodrigo
 *
 */
public class UserExistsException extends Throwable {

    /**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 7088711832826749381L;

	/**
	 * Constructor de la clase
	 * @param message
	 */
	public UserExistsException(final String message) {
        super(message);
    }

}
