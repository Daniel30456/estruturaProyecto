package co.edu.unbosque.juegoestructuraback;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JuegoestructurabackApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuegoestructurabackApplication.class, args);
	}
	@Bean
	public ModelMapper getModelMapper () {
		return new ModelMapper();
	}

}
