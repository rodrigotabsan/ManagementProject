package com.master.atrium.managementproject.validator;

/**
 * Clase que lanza la excepción de email existente
 * @author Rodrigo
 *
 */
public class EmailExistsException extends Throwable {

    /**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -431725392007042668L;

	/**
	 * Constructor de la clase
	 * @param message
	 */
	public EmailExistsException(final String message) {
        super(message);
    }

}
