package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.Account;
import org.persapiens.account.persistence.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService<T extends Account> extends CrudService<T, Long> {

	@Autowired
	private AccountRepository<T> accountRepository;

	public Optional<T> findByDescription(String description) {
		return this.accountRepository.findByDescription(description);
	}

}
