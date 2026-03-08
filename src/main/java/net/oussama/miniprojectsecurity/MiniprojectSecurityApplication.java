package net.oussama.miniprojectsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class MiniprojectSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniprojectSecurityApplication.class, args);
    }

}
