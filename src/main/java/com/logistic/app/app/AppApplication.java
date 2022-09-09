package com.logistic.app.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.logistic.app.app.models.User;
import com.logistic.app.app.repositories.UserRepository;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository repository) {
		return args -> {
			User user = new User("client", "1234");
			repository.findUserByUsername(user.getUsername()).ifPresentOrElse(s -> {
				// Doing nothing user already exits
			}, () -> repository.insert(user));
			
		};
	}

	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
