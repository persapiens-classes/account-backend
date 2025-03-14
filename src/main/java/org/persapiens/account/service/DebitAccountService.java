package org.persapiens.account.service;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.persistence.DebitAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class DebitAccountService extends AccountService<DebitAccount, DebitCategory> {

	public DebitAccountService(DebitAccountRepository debitAccountRepository, DebitCategoryService categoryService) {
		super(debitAccountRepository, categoryService);
	}

	@Override
	protected DebitAccount createAccount() {
		return new DebitAccount();
	}

}
