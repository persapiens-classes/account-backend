package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.persistence.DebitAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class DebitAccountService extends AccountService<DebitAccount> {

	private CategoryService categoryService;

	public DebitAccountService(DebitAccountRepository debitAccountRepository, CategoryService categoryService) {
		super(debitAccountRepository);
		this.categoryService = categoryService;
	}

	public DebitAccount expenseTransfer() {
		Optional<DebitAccount> findByDescription = findByDescription(DebitAccount.EXPENSE_TRANSFER);
		if (findByDescription.isEmpty()) {
			DebitAccount result = DebitAccount.builder()
				.description(DebitAccount.EXPENSE_TRANSFER)
				.category(this.categoryService.expenseTransfer())
				.build();
			return save(result);
		}
		else {
			return findByDescription.get();
		}
	}

}
