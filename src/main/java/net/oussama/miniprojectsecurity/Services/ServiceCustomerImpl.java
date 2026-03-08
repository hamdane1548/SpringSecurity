package net.oussama.miniprojectsecurity.Services;

import lombok.AllArgsConstructor;
import net.oussama.miniprojectsecurity.Dto.Login;
import net.oussama.miniprojectsecurity.Entity.Customer;
import net.oussama.miniprojectsecurity.config.UserDetailsServices;
import net.oussama.miniprojectsecurity.repository.Repositoryinterface;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCustomerImpl implements ServiceCustomer {
    private final PasswordEncoder passwordEncoder;
    private Repositoryinterface repository;
    private AuthenticationManager  authenticationManager;
    private UserDetailsServices service;
    @Override
    public void create(  Customer customer) {
         String pwd = passwordEncoder.encode(customer.getPassword());
         customer.setPassword(pwd);
         repository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Authentication login(Login login) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authentication1 = authenticationManager.authenticate(authentication);
        if(authentication1.isAuthenticated()) {
            return authentication1;
        }
        throw new BadCredentialsException("Incorrect email or password");
    }
}
