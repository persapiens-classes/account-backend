package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.domain.Account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface AccountRepository<T extends Account> extends CrudRepository<T, Long>, QueryByExampleExecutor<T> {

	Optional<T> findByDescription(String description);

}
