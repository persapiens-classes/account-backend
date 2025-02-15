package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.persistence.CategoryRepository;
import org.persapiens.account.persistence.DebitAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class DebitAccountService extends AccountService<DebitAccountDTO, DebitAccount> {

	private DebitAccountRepository debitAccountRepository;

	private CategoryService categoryService;

	public DebitAccountService(DebitAccountRepository debitAccountRepository, CategoryRepository categoryRepository,
			CategoryService categoryService) {
		super(debitAccountRepository, categoryRepository);
		this.debitAccountRepository = debitAccountRepository;
		this.categoryService = categoryService;
	}

	protected DebitAccount createAccount() {
		return new DebitAccount();
	}

	protected DebitAccountDTO createAccountDTO() {
		return new DebitAccountDTO();
	}

	public DebitAccountDTO expenseTransfer() {
		Optional<DebitAccount> findByDescription = this.debitAccountRepository
			.findByDescription(DebitAccount.EXPENSE_TRANSFER);
		if (findByDescription.isEmpty()) {
			DebitAccountDTO result = DebitAccountDTO.builder()
				.description(DebitAccount.EXPENSE_TRANSFER)
				.category(this.categoryService.expenseTransfer().getDescription())
				.build();
			return insert(result);
		}
		else {
			return toDTO(findByDescription.get());
		}
	}

}
