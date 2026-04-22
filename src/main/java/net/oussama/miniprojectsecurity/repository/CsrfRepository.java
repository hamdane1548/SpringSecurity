package net.oussama.miniprojectsecurity.repository;

import net.oussama.miniprojectsecurity.Entity.CSRF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CsrfRepository extends JpaRepository<CSRF, Long> {

    Optional<CSRF> findTokenByIdentifier(String identifier);
}
