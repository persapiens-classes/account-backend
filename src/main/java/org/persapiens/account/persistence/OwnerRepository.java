package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.domain.Owner;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Optional<Owner> findByName(String name);

    long countByNameContains(String name);

    @Transactional
    void deleteByName(String name);
}
