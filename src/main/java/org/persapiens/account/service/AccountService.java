package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.persistence.AccountRepository;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountService<T extends Account> extends CrudService<T, Long> {

	private AccountRepository<T> accountRepository;

	public Optional<T> findByDescription(String description) {
		return this.accountRepository.findByDescription(description);
	}

}
