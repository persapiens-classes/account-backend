package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.persistence.CreditAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class CreditAccountService extends AccountService<CreditAccount> {

	private CategoryService categoryService;

	public CreditAccountService(CreditAccountRepository creditAccountRepository, CategoryService categoryService) {
		super(creditAccountRepository);
		this.categoryService = categoryService;
	}

	public CreditAccount incomeTransfer() {
		Optional<CreditAccount> findByDescription = findByDescription(CreditAccount.INCOME_TRANSFER);
		if (findByDescription.isEmpty()) {
			CreditAccount result = CreditAccount.builder()
				.description(CreditAccount.INCOME_TRANSFER)
				.category(this.categoryService.incomeTransfer())
				.build();
			return save(result);
		}
		else {
			return findByDescription.get();
		}
	}

}
