package com.master.atrium.managementproject.validator;

public class UserExistsException extends Throwable {

    /**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 7088711832826749381L;

	public UserExistsException(final String message) {
        super(message);
    }

}
