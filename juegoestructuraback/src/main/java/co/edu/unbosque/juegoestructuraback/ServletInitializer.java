package co.edu.unbosque.juegoestructuraback;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JuegoestructurabackApplication.class);
		
		///holaaa hgjhgj
		
	}

}
