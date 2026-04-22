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
        String token = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN","_csrf", token);
    }

    @Override
    public void saveToken(@Nullable CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(csrfRepository);
      String identifieruser =  request.getHeader("X-IDENTIFIER");
        Optional<net.oussama.miniprojectsecurity.Entity.CSRF> user = csrfRepository.findTokenByIdentifier(identifieruser);
        if(user.isPresent()) {
            System.out.println("user found");
            net.oussama.miniprojectsecurity.Entity.CSRF extoken = user.get();
            response.setHeader("X-CSRF-TOKEN", extoken.getToken());
            extoken.setToken(token.getToken());
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
         String identifieruser =  request.getHeader("X-IDENTIFIER");
         String token = request.getHeader("X-CSRF-TOKEN");
         Optional<net.oussama.miniprojectsecurity.Entity.CSRF > existingtoken = csrfRepository.findTokenByIdentifier(identifieruser);
        if(existingtoken.isPresent()) {
           // System.out.println("existing token");
            net.oussama.miniprojectsecurity.Entity.CSRF tokencsrf = existingtoken.get();
            System.out.println(tokencsrf.getToken());
            if(tokencsrf.getToken().equals(token)) {
                return new DefaultCsrfToken("X-CSRF-TOKEN","_csrf", tokencsrf.getToken());
            }
            return  null;
        }
        System.out.println("token not found");
        return null;
    }
}
