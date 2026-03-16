package net.oussama.miniprojectsecurity.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Authentificationprovider implements AuthenticationProvider {
    private final UserDetailsServices userDetailsServices;
    private final PasswordEncoder passwordEncoder;

    public Authentificationprovider(UserDetailsServices userDetailsServices, PasswordEncoder passwordEncoder) {
        this.userDetailsServices = userDetailsServices;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Authentificationprovider");
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails heckuser = userDetailsServices.loadUserByUsername(email);
        if(passwordEncoder.matches(password,heckuser.getPassword())){
            System.out.println("Password Correct");
            return new UsernamePasswordAuthenticationToken(heckuser,password,heckuser.getAuthorities());
        }else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
