package com.master.atrium.managementproject.validator;

/**
 * Clase que lanza la excepción de que existe un dato guardado relacionado con el que se intenta eliminar
 * @author Rodrigo
 *
 */
public class RecordReferencedInOtherTablesException extends Throwable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 6935151548641018552L;

	/**
	 * Constructor de la clase
	 * @param message
	 */
	public RecordReferencedInOtherTablesException(final String message) {
        super(message);
    }

}
