package net.oussama.miniprojectsecurity.filtre;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomerAuthentificationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var authoriteies = authentication.getAuthorities();
        var auth =
                authoriteies.stream()
                        .filter(aut ->aut.getAuthority().equals("read"))
                        .findFirst();
        if (auth.isPresent()) {
            response.sendRedirect("/home");
        }else  {
            response.sendRedirect("/login");
        }
    }
}
