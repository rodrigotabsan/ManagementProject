package com.master.atrium.managementproject.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validador personalizado de contraseñas
 * @author Rodrigo
 *
 */
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordValidator {

	/** El mensaje */
	String message() default "Passwords do not match";

	/** Grupos */
    Class<?>[] groups() default {};
    /** Payload */
    Class<? extends Payload>[] payload() default {};	
}
