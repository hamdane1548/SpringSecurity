package net.oussama.miniprojectsecurity.repository;

import net.oussama.miniprojectsecurity.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repositoryinterface extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
