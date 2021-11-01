package com.master.atrium.managementproject.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.service.ResetPasswordService;
import com.master.atrium.managementproject.utility.UtilEMail;
import com.master.atrium.managementproject.validator.EmailExistsException;
import com.master.atrium.managementproject.validator.UserExistsException;

/**
 * Clase que permite realizar la restauración de la contraseña
 * @author Rodrigo
 *
 */
public class ResetPasswordServiceImpl implements ResetPasswordService{
	/**
	 * Inyección de {@link PasswordEncoder}
	 */
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/**
	 * Inyección de {@link PersonRepository}
	 */
	@Autowired
	PersonRepository personRepository;
	
	public ResetPasswordServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
     * Restaura la contraseña y envía un correo al propietario de la cuenta
     * @param person el usuario
	 * @throws UserExistsException 
	 * @throws EmailExistsException 
     */
	@Override
	public void resetPassword(Person person) {
		UtilEMail emailUtil = new UtilEMail();
		List<String> emails = new ArrayList<>();
		String password = generateCommonTextPassword();
		person.setPassword(passwordEncoder.encode(password));
		personRepository.updatePassword(person);
		emails.add(person.getEmail());
		emailUtil.sendEmail(emails, new Date() + ": Your account has been reset succesfully." , "This is an email to let you know that your account has been reset succesfully. Remember that your username is "+person.getUser()+" and your new password is "+ password);
	}
	    
    /**
     * Generar caracteres especiales aleatorios
     * @param length
     * @return
     */
    private String generateRandomSpecialCharacters(int length) {
        return RandomStringUtils.randomAscii(33, 45).substring(0, length);
    }
    
    /**
     * Generar texto común para contraseña
     * @return
     */
    private String generateCommonTextPassword() {
        String pwString = generateRandomSpecialCharacters(2).concat(RandomStringUtils.randomNumeric(2))
          .concat(RandomStringUtils.randomAlphabetic(4))
          .concat(RandomStringUtils.randomAlphanumeric(2));
        List<Character> pwChars = pwString.chars()
          .mapToObj(data -> (char) data)
          .collect(Collectors.toList());
        Collections.shuffle(pwChars);
        return pwChars.stream()
          .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
          .toString();
    }

}
