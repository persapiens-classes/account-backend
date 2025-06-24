package org.persapiens.account.service;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.persistence.EquityAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class EquityAccountService extends AccountService<EquityAccount, EquityCategory> {

	public EquityAccountService(EquityAccountRepository equityAccountRepository, EquityCategoryService equityCategoryService) {
		super(equityAccountRepository, equityCategoryService);
	}

	@Override
	protected EquityAccount createAccount() {
		return new EquityAccount();
	}

}
