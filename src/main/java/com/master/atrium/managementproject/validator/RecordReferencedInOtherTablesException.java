package com.master.atrium.managementproject.validator;

public class RecordReferencedInOtherTablesException extends Throwable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 6935151548641018552L;

	public RecordReferencedInOtherTablesException(final String message) {
        super(message);
    }

}
