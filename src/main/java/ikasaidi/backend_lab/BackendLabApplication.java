package ikasaidi.backend_lab;

import ikasaidi.backend_lab.services.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendLabApplication.class, args);
    }

    @Bean
    CommandLineRunner run(PersonService personService) {
        return args -> {

                personService.bdFromTheStart();

        };


    }

}
