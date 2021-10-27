package com.master.atrium.managementproject.validator;


import java.lang.invoke.MethodHandles;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.master.atrium.managementproject.entity.Person;

/**
 * Validador de coincidencia de contraseña
 * @author Rodrigo
 *
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordValidator, Object>{
	/** Log de la clase */
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	/**
	 * {@inheritDoc}
	 */
	@Override
    public void initialize(final PasswordValidator constraintAnnotation) {
		LOG.debug("INICIALIZANDO VALIDADOR DE CONTRASEÑAS");
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
    	
    	Person person = null;    	
    	boolean valid = Boolean.FALSE;
    	
    	if(obj instanceof Person) {
    		person = (Person) obj;
    	}
    	
    	if(Objects.nonNull(person) 
    			&& Objects.nonNull(person.getPassword()) 
    			&& Objects.nonNull(person.getPasswordConfirmation())) {
    		valid = person.getPassword().equals(person.getPasswordConfirmation());
    	}
    	
    	return valid;
    }

}
