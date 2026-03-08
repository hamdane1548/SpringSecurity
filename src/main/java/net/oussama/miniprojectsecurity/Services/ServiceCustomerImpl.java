package net.oussama.miniprojectsecurity.Services;

import lombok.AllArgsConstructor;
import net.oussama.miniprojectsecurity.Entity.Customer;
import net.oussama.miniprojectsecurity.repository.Repositoryinterface;
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
}
