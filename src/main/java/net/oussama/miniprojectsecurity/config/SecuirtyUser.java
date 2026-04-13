package net.oussama.miniprojectsecurity.config;

import net.oussama.miniprojectsecurity.Entity.Customer;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecuirtyUser implements UserDetails {
    private final Customer customer;
    private final List<GrantedAuthority> grantedAuthorities;
    public SecuirtyUser(Customer customer, List<GrantedAuthority> grantedAuthorities) {
        this.customer = customer;
        this.grantedAuthorities = grantedAuthorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  grantedAuthorities;
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
