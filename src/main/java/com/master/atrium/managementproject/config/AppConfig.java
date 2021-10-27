package com.master.atrium.managementproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Clase de configuracion que importa las demás configuraciones
 * @author Rodrigo
 *
 */
@Configuration
@Import({ AppMvcConfig.class, SpringSecurityConfig.class })
public class AppConfig {
	/**
	 * Constructor de la clase
	 */
	public AppConfig() {
		super();
	}

}
