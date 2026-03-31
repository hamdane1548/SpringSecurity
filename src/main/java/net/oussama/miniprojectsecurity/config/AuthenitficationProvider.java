package net.oussama.miniprojectsecurity.config;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenitficationProvider implements AuthenticationProvider {
    private UserDetailsServicesImpl userDetailsServicesImpl;
    private PasswordEncoderImpl passwordEncoder;
    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println("Username: " + username);
        SecuirtyUser u = (SecuirtyUser) userDetailsServicesImpl.loadUserByUsername(username);
        if(passwordEncoder.matches(password, u.getPassword())) {
            System.out.println("Password Match");
            return new UsernamePasswordAuthenticationToken(username, password, u.getAuthorities());
        }else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
