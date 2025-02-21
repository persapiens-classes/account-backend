package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.domain.Account;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Primary
public interface AccountRepository<T extends Account> extends CrudRepository<T, Long> {

	Optional<T> findByDescription(String description);

	@Transactional
	long deleteByDescription(String description);

}
