package com.master.atrium.managementproject.validator;

public class EmailExistsException extends Throwable {

    /**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -431725392007042668L;

	public EmailExistsException(final String message) {
        super(message);
    }

}
