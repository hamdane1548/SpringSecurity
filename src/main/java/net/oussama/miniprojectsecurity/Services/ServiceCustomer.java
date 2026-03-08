package net.oussama.miniprojectsecurity.Services;

import net.oussama.miniprojectsecurity.Dto.Login;
import net.oussama.miniprojectsecurity.Entity.Customer;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ServiceCustomer {
    void create(Customer customer);
    List<Customer> findAll();
    Authentication login(Login login);
}
