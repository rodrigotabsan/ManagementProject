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

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private UserDetails userDetails;
    @Autowired
    private PersonRepository personRepository;

    public UserDetails loadUserByUsername(final String user) throws UsernameNotFoundException {
        final Person person = personRepository.findByUser(user);
        if (Objects.isNull(person)) {
            throw new UsernameNotFoundException("No user found with username: " + user);
        }
        setUserDetails(new User(person.getUser(), person.getPassword(), true, true, true, true, getAuthorities(person.getRole().getName())));
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

	/**
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * @param userDetails the userDetails to set
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
   
}
