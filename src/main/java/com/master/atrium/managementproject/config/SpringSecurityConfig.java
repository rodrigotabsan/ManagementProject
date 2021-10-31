package com.master.atrium.managementproject.config;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.master.atrium.managementproject.entity.Person;
import com.master.atrium.managementproject.entity.Role;
import com.master.atrium.managementproject.repository.PersonRepository;
import com.master.atrium.managementproject.repository.RoleRepository;
import com.master.atrium.managementproject.service.impl.UserDetailsServiceImpl;

/**
 * Clase de configuración para Spring Security
 * @author Rodrigo
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	/** Constante ADMIN */
	private static final String ADMIN = "ADMIN";
	/** Constante USER */
	private static final String USER = "USER";
	
	/** Recursos estáticos a permitir */
	private String[] staticResources  =  {
	        "/css/**",
	        "/img/**",
	        "/js/**",
	    };
	
	/**
	 * Inyección {@link UserDetailsServiceImpl}
	 */
	@Autowired
    private UserDetailsServiceImpl userDetailsService;
	
	/**
	 * Inyección {@link PersonRepository}
	 */
	@Autowired
    private PersonRepository personRepository;
	
	/**
	 * Inyección {@link RoleRepository}
	 */
	@Autowired
    private RoleRepository roleRepository;
	
	/**
	 * Constructor de la clase
	 */
	public SpringSecurityConfig() {
        super();
    }	
		
	/**
	 * Inyección para la configuración global
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
	
	/**
	 * Crea un nuevo {@link PasswordEncoder} de tipo {@link BCryptPasswordEncoder}
	 * @return
	 */
	@Bean
	public static PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(12);
	}
	
	/**
	 * Guarda los roles y las personas de prueba
	 */
	@PostConstruct
    private void saveTestUser() {
		createTestRolesAndPersons();	
    }
	
	/**
	 * Crea roles de prueba y personas de prueba
	 */
	private void createTestRolesAndPersons() {
		Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
		Role roleUser = roleRepository.findByName("ROLE_USER");
		
		if(Objects.isNull(roleAdmin)) {
			final Role newRole = new Role();
			newRole.setName("ROLE_ADMIN");
			roleRepository.insert(newRole);
			roleAdmin = roleRepository.findByName(newRole.getName());
		}
		
		if(Objects.isNull(roleUser)) {
			final Role newRole = new Role();
			newRole.setName("ROLE_USER");
			roleRepository.insert(newRole);
			roleUser = roleRepository.findByName(newRole.getName());
		}
		
		createPersons(roleAdmin, roleUser);
	}
	
	/**
	 * Crea personas de prueba
	 * @param roleAdmin
	 * @param roleUser
	 */
	private void createPersons(Role roleAdmin, Role roleUser) {
		Person personAdmin = personRepository.findByEmail("test@email.com");
		Person personUser = personRepository.findByEmail("test2@email.com");
		
		if(Objects.isNull(personAdmin)) {
	        final Person newPerson = new Person();
	        newPerson.setUser("rodrigo");
	        newPerson.setEmail("test@email.com");
	        newPerson.setPassword(passwordEncoder().encode("pass"));
	        newPerson.setDni("12345678Z");
	        newPerson.setName("Rodrigo");
	        newPerson.setLastname1("Tablado");
	        newPerson.setLastname2("Sánchez");
	        newPerson.setStartDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
	        newPerson.setRole(roleAdmin);        
	        personRepository.insert(newPerson);
		}
		
		if(Objects.isNull(personUser)) {
	        final Person newPerson = new Person();
	        newPerson.setUser("rodrigo2");
	        newPerson.setEmail("test2@email.com");
	        newPerson.setPassword(passwordEncoder().encode("pass"));
	        newPerson.setDni("12345678Z");
	        newPerson.setName("Rodrigo");
	        newPerson.setLastname1("Tablado");
	        newPerson.setLastname2("Sánchez");
	        newPerson.setStartDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
	        newPerson.setRole(roleUser);        
	        personRepository.insert(newPerson);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception { 
	    http
	      .authorizeRequests()
		      .antMatchers("/person/delete/**").hasRole(ADMIN)
		      .antMatchers("/delete/**").hasRole(ADMIN)
		      .antMatchers("/person/modify/**").hasAnyRole(ADMIN)
		      .antMatchers("/doLogin/**").hasAnyRole(ADMIN, USER)
		      .antMatchers("/person/**").hasAnyRole(ADMIN, USER)
		      .antMatchers(staticResources).permitAll()
		      .anyRequest().authenticated()
		  .and()
		  	  .formLogin()
		  	  .loginPage("/login").permitAll()
		  	  .loginProcessingUrl("/doLogin")
		  	  .usernameParameter("user")
	          .passwordParameter("password")
		  .and()
		  	  .logout()
		  	  .permitAll()
		  	  .logoutRequestMatcher(new AntPathRequestMatcher("/doLogout", "GET")).clearAuthentication(Boolean.TRUE)
		  	/*http.authorizeRequests()
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        .and()
           .formLogin().loginPage("/login").failureUrl("/login?error")
                .usernameParameter("user").passwordParameter("password")
        .and()
            .logout().logoutSuccessUrl("/login?logout")
        .and()
            .exceptionHandling().accessDeniedPage("/403")*/
	      .and()
	      .csrf()
	      	  .disable();
	}

}
