package net.oussama.miniprojectsecurity.CSRF;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.oussama.miniprojectsecurity.repository.CsrfRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Component
public class CSRF implements CsrfTokenRepository {
    @Autowired
    private  CsrfRepository csrfRepository;
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        System.out.println("im here 2 ");
        String token = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN","_csrf", token);
    }

    @Override
    public void saveToken(@Nullable CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("im here 1 ");
        System.out.println("token: " + token.getToken());
      String identifieruser =  request.getHeader("X-IDENTIFIER");
      if(identifieruser==null){
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          throw new RuntimeException("X-IDENTIFIER header not found");
      }
        Optional<net.oussama.miniprojectsecurity.Entity.CSRF> user = csrfRepository.findTokenByIdentifier(identifieruser);
        if(user.isPresent()) {
            System.out.println("user found"+user);
            net.oussama.miniprojectsecurity.Entity.CSRF extoken = user.get();
            extoken.setToken(token.getToken());
            csrfRepository.save(extoken);
            response.setHeader("X-CSRF-TOKEN", extoken.getToken());
            response.setHeader("_csrf", extoken.getToken());
        }else {
            net.oussama.miniprojectsecurity.Entity.CSRF tokencsrf = new net.oussama.miniprojectsecurity.Entity.CSRF();
            tokencsrf.setToken(token.getToken());
            tokencsrf.setIdentifier(identifieruser);
            response.setHeader("X-CSRF-TOKEN", tokencsrf.getToken());
            csrfRepository.save(tokencsrf);
        }
    }

    @Override
    public @Nullable CsrfToken loadToken(HttpServletRequest request) {
        System.out.println("im here 3");
        String identifieruser =  request.getHeader("X-IDENTIFIER");
         String token = request.getHeader("X-CSRF-TOKEN");
         Optional<net.oussama.miniprojectsecurity.Entity.CSRF > existingtoken = csrfRepository.findTokenByIdentifier(identifieruser);
        if(existingtoken.isPresent()) {
           System.out.println("existing token");
            net.oussama.miniprojectsecurity.Entity.CSRF tokencsrf = existingtoken.get();
            System.out.println(tokencsrf.getToken());
            System.out.println(tokencsrf.getToken()+"ddd"+token);
            if(existingtoken.get().getToken().equals(token)) {
                System.out.println("token found testtt");
                CsrfToken tokentest = new DefaultCsrfToken("X-CSRF-TOKEN","_csrf", tokencsrf.getToken());
                return (CsrfToken) tokentest;
            }
            return  null;
        }
        return null;
    }
}
