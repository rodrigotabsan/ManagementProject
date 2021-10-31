package com.master.atrium.managementproject.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.repository.PersonRepository;

/**
 * Implementación del servicio de detalles del usuario para la autenticación
 * @author Rodrigo
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	/**
	 * {@link UserDetails}
	 */
	private UserDetails userDetails;
	/**
	 * Inyección de repositorio de personas
	 */
    @Autowired
    private PersonRepository personRepository;

    /**
     * Carga el usuario por nombre de usuario
     * @throws UsernameNotFoundException si no encuentra el nombre de usuario o si el usuario está desactivado.
     */
    public UserDetails loadUserByUsername(final String user) throws UsernameNotFoundException {
        final Person person = personRepository.findByUser(user);
        if (Objects.isNull(person)) {
            throw new UsernameNotFoundException("No user found with username: " + user);
        }
        if (Objects.nonNull(person.getEndDate())) {
            throw new UsernameNotFoundException("User: " + user + " is disabled.");
        }
        setUserDetails(new User(person.getUser(), person.getPassword(), true, true, true, true, getAuthorities(person.getRole().getName())));
        return userDetails;
    }

    /**
     * Obtiene el listado de autorizaciones pasando un rol como parámetro
     * @param role El rol
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

	/**
	 * Obtiene los {@link UserDetails}
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * Sobrescribe los {@link UserDetails}
	 * @param userDetails the userDetails to set
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
   
}
