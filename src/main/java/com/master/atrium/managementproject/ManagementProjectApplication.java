package com.master.atrium.managementproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.master.atrium.managementproject.config.AppConfig;


/**
 * Clase principal que permite la ejecución de la aplicación
 * @author Rodrigo
 *
 */
@SpringBootApplication
public class ManagementProjectApplication {

	/**
	 * Permite la ejecución del proyecto. Además, se incluye la configuración de este.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(new Class[] {ManagementProjectApplication.class, AppConfig.class}, args);
	}
	
	/**
	 * Permite visualizar los html de la aplicación
	 * @return
	 */
	@Bean
	  public ViewResolver viewResolver() {
	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setTemplateMode("XHTML");
	    templateResolver.setPrefix("templates/");
	    templateResolver.setSuffix(".html");

	    SpringTemplateEngine engine = new SpringTemplateEngine();
	    engine.setTemplateResolver(templateResolver);

	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	    viewResolver.setTemplateEngine(engine);
	    return viewResolver;
	  }

}
