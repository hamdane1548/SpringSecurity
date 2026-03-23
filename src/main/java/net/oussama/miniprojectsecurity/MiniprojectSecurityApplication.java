package net.oussama.miniprojectsecurity;

import net.oussama.miniprojectsecurity.Entity.Customer;
import net.oussama.miniprojectsecurity.repository.Repositoryinterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class MiniprojectSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniprojectSecurityApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(Repositoryinterface repositoryinterface) {
        return args -> {
          Customer customer = Customer.builder()
                  .firstName("John")
                  .password("12345")
                  .grantedAuthorities("READ")
                  .email("oussama@gmail.com")
                  .build();
          repositoryinterface.save(customer);
        };

    }
}
