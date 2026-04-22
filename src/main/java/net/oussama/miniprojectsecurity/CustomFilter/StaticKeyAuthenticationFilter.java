package net.oussama.miniprojectsecurity.CustomFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/*
import java.io.IOException;
@Component
public class StaticKeyAuthenticationFilter implements Filter {
    @Value("${authorization.key}")
    private String authorizationKey;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest requestheader = (HttpServletRequest) servletRequest;
      HttpServletResponse  responseheader = (HttpServletResponse) servletResponse;
      String Header = requestheader.getHeader("Authorization");
      if(Header.equals(authorizationKey)){
          System.out.println("hhel");
          filterChain.doFilter(servletRequest, servletResponse);
      }else {
          responseheader.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    }
}
*\
 */
