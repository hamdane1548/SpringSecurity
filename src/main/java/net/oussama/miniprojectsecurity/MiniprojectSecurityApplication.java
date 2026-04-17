package net.oussama.miniprojectsecurity;

import net.oussama.miniprojectsecurity.Entity.Customer;
import net.oussama.miniprojectsecurity.repository.Repositoryinterface;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@EnableWebSecurity
@EnableAsync
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
          repositoryinterface.save(Customer.builder()
                          .firstName("Oussama")
                          .password("12345")
                          .grantedAuthorities("ROLE_MANAGER")
                          .email("hamdane@gmail.com")
                  .build());
        };

    }
    @Bean
    public InitializingBean init() {
        System.out.println("init method");
        return  () -> SecurityContextHolder.setStrategyName(
                SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
        );
    }
}
