package org.persapiens.account.service;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.persistence.CreditAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class CreditAccountService extends AccountService<CreditAccount, CreditCategory> {

	public CreditAccountService(CreditAccountRepository creditAccountRepository, CreditCategoryService creditCategoryService) {
		super(creditAccountRepository, creditCategoryService);
	}

	@Override
	protected CreditAccount createAccount() {
		return new CreditAccount();
	}
}
