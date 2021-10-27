package com.master.atrium.managementproject.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.master.atrium.managementproject.service.impl.PersonServiceImpl;
import com.master.atrium.managementproject.service.impl.ProjectServiceImpl;
import com.master.atrium.managementproject.service.impl.RoleServiceImpl;
import com.master.atrium.managementproject.service.impl.TaskServiceImpl;

/**
 * Clase de configuracion para Spring MVC
 * @author Rodrigo
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.master.atrium.managementproject.repository")
@EntityScan("com.master.atrium.managementproject.entity")
@EnableWebMvc
public class AppMvcConfig implements WebMvcConfigurer {
	
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	@Bean
	public PersonServiceImpl personService() {
		return new PersonServiceImpl();
	}
	
	@Bean
	public ProjectServiceImpl projectService() {
		return new ProjectServiceImpl();
	}
	
	@Bean
	public TaskServiceImpl taskService() {
		return new TaskServiceImpl();
	}
	
	@Bean
	public RoleServiceImpl roleService() {
		return new RoleServiceImpl();
	}
	
	/**
	 * Constructor de la clase
	 */
	public AppMvcConfig() {
		super();
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
