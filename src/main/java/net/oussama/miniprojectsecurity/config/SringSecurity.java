package net.oussama.miniprojectsecurity.config;

import lombok.AllArgsConstructor;
import net.oussama.miniprojectsecurity.CustomFilter.AuthenticationLoggingFilter;
import net.oussama.miniprojectsecurity.CustomFilter.RequestValidationFilter;
import net.oussama.miniprojectsecurity.filtre.CustomAuthenticationFailureHandler;
import net.oussama.miniprojectsecurity.filtre.CustomerAuthentificationSuccessHandler;
import net.oussama.miniprojectsecurity.filtre.CustomerEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@Configuration
@RestController
public class SringSecurity {
    @Autowired
     private CustomerAuthentificationSuccessHandler  customerAuthentificationSuccessHandler;
     private CustomAuthenticationFailureHandler   customAuthenticationFailureHandler;
    @Bean
    SecurityFilterChain deafultFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth ->
                  auth.requestMatchers("/create").permitAll()
                          .requestMatchers("/fetch","/hello","/bye","/test","/home").authenticated()
                          .requestMatchers("/authorisation").hasRole("MANAGER")
                          .requestMatchers("/user","/auth").permitAll()
                          .requestMatchers(HttpMethod.GET, "/a").authenticated()
                          .requestMatchers(HttpMethod.POST, "/a").permitAll()
                          .requestMatchers("/product/{code:^[0-9]*$}").permitAll()
                         // .requestMatchers("/video/{country:.*/(usa|uk|canada)}/{langage}").authenticated()
                          .anyRequest().denyAll()
                );
        
       // http.authenticationProvider(AuthenitficationProvider.class.newInstance());
        http.httpBasic(customizer ->{
            customizer.realmName("MiniProjectSecurity");
            customizer.authenticationEntryPoint(new CustomerEntryPoint());
        });
        http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class);
        http.addFilterAfter(new AuthenticationLoggingFilter(),BasicAuthenticationFilter.class);
        http.formLogin(customizer ->{
            customizer.successHandler(
                    customerAuthentificationSuccessHandler
            );
            customizer.failureHandler(customAuthenticationFailureHandler);
        });
        return http.build();
    }
    @Bean
    PasswordEncoderImpl passwordEncoder() {
        return new PasswordEncoderImpl();
    }



}
