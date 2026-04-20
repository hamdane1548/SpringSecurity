package net.oussama.miniprojectsecurity.CustomFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RequestValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpsRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
      String header  = httpsRequest.getHeader("Request_id");
      if(header== null || header.isBlank()){
          httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
         return;
      }
      filterChain.doFilter( httpsRequest,httpResponse);
    }
}
