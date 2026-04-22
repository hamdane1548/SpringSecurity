package net.oussama.miniprojectsecurity.config;

import lombok.AllArgsConstructor;
import net.oussama.miniprojectsecurity.CSRF.CSRF;
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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
@RestController
@AllArgsConstructor
public class SringSecurity {

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
                          .requestMatchers("/product/test","/product/gettoken").authenticated()
                          .requestMatchers("/product/test3").authenticated()
                         // .requestMatchers("/video/{country:.*/(usa|uk|canada)}/{langage}").authenticated()
                          .anyRequest().denyAll()
                );
        http.cors(Customizer.withDefaults());
        http.csrf(customCsrf ->{
            customCsrf.ignoringRequestMatchers("/test1");
            System.out.println("---------------init of customCsrf");
        });
       // http.authenticationProvider(AuthenitficationProvider.class.newInstance());
        http.httpBasic(customizer ->{
            customizer.realmName("MiniProjectSecurity");
            customizer.authenticationEntryPoint(new CustomerEntryPoint());
        });
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:8089"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
