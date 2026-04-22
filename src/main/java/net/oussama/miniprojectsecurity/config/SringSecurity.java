package net.oussama.miniprojectsecurity.config;

import lombok.AllArgsConstructor;
import net.oussama.miniprojectsecurity.CSRF.CSRF;
import net.oussama.miniprojectsecurity.CustomFilter.AuthenticationLoggingFilter;
import net.oussama.miniprojectsecurity.CustomFilter.CsrfTokenLogger;
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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@Configuration
@RestController
@AllArgsConstructor
public class SringSecurity {
    @Bean
    public CsrfTokenRepository customTokenRepository() {
        return new CSRF();
    }
     private CustomerAuthentificationSuccessHandler  customerAuthentificationSuccessHandler;
     private CustomAuthenticationFailureHandler   customAuthenticationFailureHandler;
    @Bean
    SecurityFilterChain deafultFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth ->
                  auth.requestMatchers("/create").permitAll()
                          .requestMatchers("/fetch","/hello","/bye","/test","/home").authenticated()
                          .requestMatchers("/authorisation").hasRole("MANAGER")
                          .requestMatchers("/user","/auth").permitAll()
                          .requestMatchers(HttpMethod.GET, "/a").authenticated()
                          .requestMatchers("/main","/product/add","/csrf").authenticated()
                          .requestMatchers(HttpMethod.POST, "/a").permitAll()
                          .requestMatchers("/product/{code:^[0-9]*$}").permitAll()

                         // .requestMatchers("/video/{country:.*/(usa|uk|canada)}/{langage}").authenticated()
                          .anyRequest().denyAll()
                );
        http.csrf(customCsrf ->{
            customCsrf.ignoringRequestMatchers("/test1");
            customCsrf.csrfTokenRepository(customTokenRepository());
        });
       // http.authenticationProvider(AuthenitficationProvider.class.newInstance());
        http.httpBasic(customizer ->{
            customizer.realmName("MiniProjectSecurity");
            customizer.authenticationEntryPoint(new CustomerEntryPoint());
        });
        http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class);
       // http.addFilterAt(staticKeyAuthenticationFilter, BasicAuthenticationFilter.class);
       // http.addFilterBefore(new RequestValidationFilter(), StaticKeyAuthenticationFilter.class);
      //  http.addFilterAfter(new AuthenticationLoggingFilter(),StaticKeyAuthenticationFilter.class);
        http.formLogin(customizer ->{
            customizer.successHandler(
                    customerAuthentificationSuccessHandler
            );
            customizer.failureHandler(customAuthenticationFailureHandler);
            customizer.defaultSuccessUrl("/main",true);
        });
        return http.build();
    }
    @Bean
    PasswordEncoderImpl passwordEncoder() {
        return new PasswordEncoderImpl();
    }



}
