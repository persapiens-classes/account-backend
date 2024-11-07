package org.persapiens.account.service;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.persistence.EquityAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class EquityAccountService extends AccountService<EquityAccount> {

	public EquityAccountService(EquityAccountRepository equityAccountRepository) {
		super(equityAccountRepository);
	}

}
