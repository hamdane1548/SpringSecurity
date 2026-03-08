package net.oussama.miniprojectsecurity.controlleur;

import lombok.AllArgsConstructor;
import net.oussama.miniprojectsecurity.Dto.Login;
import net.oussama.miniprojectsecurity.Entity.Customer;
import net.oussama.miniprojectsecurity.Services.ServiceCustomerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping
public class Customers {
    private ServiceCustomerImpl  serviceCustomerImpl;
    @PostMapping("/create")
    ResponseEntity<Customer> create( @RequestBody  Customer customer) {
        System.out.println(customer.getFirstName() + customer.getFirstName());
          serviceCustomerImpl.create(customer);
          return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
    @GetMapping("/fetch")
    public  ResponseEntity<Collection<Customer>> fetch() {
        List<Customer> customers = serviceCustomerImpl.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }
}
