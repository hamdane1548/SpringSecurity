package net.oussama.miniprojectsecurity.CustomFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

public class CsrfTokenLogger extends OncePerRequestFilter {
    private Logger logger =
            Logger.getLogger(CsrfTokenLogger.class.getName());
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Object token = request.getAttribute("_csrf");
        CsrfToken csrfToken = (CsrfToken) token;
        logger.info("CSRF Token: " + csrfToken.getToken());
        filterChain.doFilter(request,response);
    }
}
