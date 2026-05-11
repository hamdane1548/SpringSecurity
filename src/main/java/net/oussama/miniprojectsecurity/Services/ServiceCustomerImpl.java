package net.oussama.miniprojectsecurity.Services;

import lombok.AllArgsConstructor;
import net.oussama.miniprojectsecurity.Entity.Customer;
import net.oussama.miniprojectsecurity.repository.Repositoryinterface;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCustomerImpl implements ServiceCustomer {
    private final PasswordEncoder passwordEncoder;
    private Repositoryinterface repository;
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
    @PreAuthorize("hasAuthority('WRITE')")
    @Override
    public Authentication findName() throws AccessDeniedException {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return authentication;
    }

}
