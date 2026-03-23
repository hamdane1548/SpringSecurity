package net.oussama.miniprojectsecurity.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.oussama.miniprojectsecurity.Entity.Customer;
import net.oussama.miniprojectsecurity.repository.Repositoryinterface;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserDetailsServices implements UserDetailsService {
    private Repositoryinterface repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = repository.findByEmail(email);
        List<GrantedAuthority> grantedAuthorities =  List.of(new SimpleGrantedAuthority(customer.getGrantedAuthorities()));
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }
        SecuirtyUser user = new SecuirtyUser(customer);
        return  user;
    }
}
