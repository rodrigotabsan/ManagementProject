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
    	String specialChars = "~!@#$%^&*,_?";
    	String numbers = "0123456789";
    	String lowercase = "abcdefghijklmnñopqrstuvwxyz";
    	String uppercase = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    	
    	if(obj instanceof Person) {
    		person = (Person) obj;
    	}
    	
    	if(Objects.nonNull(person) 
    			&& Objects.nonNull(person.getPassword()) 
    			&& Objects.nonNull(person.getPasswordConfirmation())    			
    			&& verifyChars(person.getPassword(), specialChars)
    			&& verifyChars(person.getPassword(), numbers)
    			&& verifyChars(person.getPassword(), lowercase)
    			&& verifyChars(person.getPassword(), uppercase)
    			&& person.getPassword().equals(person.getPasswordConfirmation())) {
    		valid = Boolean.TRUE;
    	}
    	
    	return valid;
    }
    
    /**
     * Verifica los caracteres
     * @param password la contraseña a verificar
     * @param chars los caracteres que sirven para verificar la contraseña
     * @return
     */
    private boolean verifyChars(String password, String chars) {
    	
    	int indexSpecialChars = 0;
    	boolean hasSpecialChars = Boolean.TRUE;
    	while(indexSpecialChars < chars.length() && !hasSpecialChars) {
    		hasSpecialChars = password.contains(String.valueOf(chars.charAt(indexSpecialChars)));
    		indexSpecialChars++;
    	}
    	return hasSpecialChars;
    }
    

}
