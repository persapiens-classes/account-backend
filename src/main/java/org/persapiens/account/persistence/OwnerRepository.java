package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.domain.Owner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

	Optional<Owner> findByName(String name);

	@Transactional
	long deleteByName(String name);

}
