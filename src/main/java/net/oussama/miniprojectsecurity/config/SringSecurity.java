package net.oussama.miniprojectsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SringSecurity {

    @Bean
    SecurityFilterChain deafultFilterChain(HttpSecurity http) throws Exception {
        System.out.println("heelo my freiend");
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth ->
                  auth.requestMatchers("/create").permitAll()
                          .requestMatchers("/fetch").authenticated()
                          .requestMatchers("/user","/auth").permitAll()
                );
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
