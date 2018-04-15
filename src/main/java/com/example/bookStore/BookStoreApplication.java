package com.example.bookStore;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookStoreApplication extends SpringBootServletInitializer {

	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BookStoreApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}
}

