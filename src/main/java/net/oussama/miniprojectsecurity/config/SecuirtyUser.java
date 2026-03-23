package net.oussama.miniprojectsecurity.config;

import net.oussama.miniprojectsecurity.Entity.Customer;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecuirtyUser implements UserDetails {
    private final Customer customer;
    public SecuirtyUser(Customer customer) {
        this.customer = customer;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getFirstName();
    }
}
