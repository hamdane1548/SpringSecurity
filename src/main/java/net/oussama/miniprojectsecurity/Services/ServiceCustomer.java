package net.oussama.miniprojectsecurity.Services;

import net.oussama.miniprojectsecurity.Entity.Customer;

import java.util.List;

public interface ServiceCustomer {
    void create(Customer customer);
    List<Customer> findAll();
}
