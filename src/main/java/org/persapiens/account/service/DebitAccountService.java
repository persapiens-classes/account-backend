package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.persistence.DebitAccountRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DebitAccountService extends AccountService<DebitAccountDTO, DebitAccount> {

	private DebitAccountRepository debitAccountRepository;

	private CategoryService categoryService;

	public DebitAccountService(DebitAccountRepository debitAccountRepository, CategoryService categoryService) {
		super(debitAccountRepository, categoryService);
		this.debitAccountRepository = debitAccountRepository;
		this.categoryService = categoryService;
	}

	@Override
	protected DebitAccount createAccount() {
		return new DebitAccount();
	}

	@Override
	protected DebitAccountDTO createAccountDTO(String description, String category) {
		return new DebitAccountDTO(description, category);
	}

	@Transactional
	public DebitAccountDTO expenseTransfer() {
		Optional<DebitAccount> findByDescription = this.debitAccountRepository
			.findByDescription(DebitAccount.EXPENSE_TRANSFER);
		if (findByDescription.isEmpty()) {
			DebitAccount result = DebitAccount.builder()
				.description(DebitAccount.EXPENSE_TRANSFER)
				.category(this.categoryService.expenseTransfer())
				.build();
			return toDTO(this.debitAccountRepository.save(result));
		}
		else {
			return toDTO(findByDescription.get());
		}
	}

}
