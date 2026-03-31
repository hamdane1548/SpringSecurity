package net.oussama.miniprojectsecurity.controlleur;

import lombok.AllArgsConstructor;
import net.oussama.miniprojectsecurity.Dto.Login;
import net.oussama.miniprojectsecurity.Entity.Customer;
import net.oussama.miniprojectsecurity.Services.ServiceCustomerImpl;
import net.oussama.miniprojectsecurity.config.SecuirtyUser;
import net.oussama.miniprojectsecurity.config.SimpleUser;
import net.oussama.miniprojectsecurity.config.UserDetailsServicesImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping
public class Customers {
    private ServiceCustomerImpl  serviceCustomerImpl;
    private UserDetailsServicesImpl userDetailsservicesImpl;
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
    @GetMapping("user")
    public ResponseEntity<UserDetails> getUser() {
        UserDetails user = new SimpleUser("oussama","12345");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return ResponseEntity.ok(user);

    }
    @GetMapping("/hello")
    public String hello(Authentication a) {
        return "Hello, " + a.getName() + "!";
    }
    @PostMapping("/auth")
    public ResponseEntity<Authentication> login(@RequestBody Login login) {
        SecuirtyUser user = (SecuirtyUser) userDetailsservicesImpl.loadUserByUsername(login.getEmail());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println(authentication);
        return ResponseEntity.ok(authentication);
    }
}
