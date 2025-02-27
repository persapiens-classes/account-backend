package org.persapiens.account.service;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.persistence.EquityAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class EquityAccountService extends AccountService<EquityAccountDTO, EquityAccount> {

	public EquityAccountService(EquityAccountRepository equityAccountRepository, CategoryService categoryService) {
		super(equityAccountRepository, categoryService);
	}

	protected EquityAccount createAccount() {
		return new EquityAccount();
	}

	protected EquityAccountDTO createAccountDTO(String description, String category) {
		return new EquityAccountDTO(description, category);
	}

}
