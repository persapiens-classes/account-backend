package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.persistence.CategoryRepository;
import org.persapiens.account.persistence.CreditAccountRepository;

import org.springframework.stereotype.Service;

@Service
public class CreditAccountService extends AccountService<CreditAccountDTO, CreditAccount> {

	private CreditAccountRepository creditAccountRepository;

	private CategoryService categoryService;

	public CreditAccountService(CreditAccountRepository creditAccountRepository, CategoryRepository categoryRepository,
			CategoryService categoryService) {
		super(creditAccountRepository, categoryRepository);
		this.creditAccountRepository = creditAccountRepository;
		this.categoryService = categoryService;
	}

	protected CreditAccount createAccount() {
		return new CreditAccount();
	}

	protected CreditAccountDTO createAccountDTO() {
		return new CreditAccountDTO();
	}

	public CreditAccountDTO incomeTransfer() {
		Optional<CreditAccount> findByDescription = this.creditAccountRepository
			.findByDescription(CreditAccount.INCOME_TRANSFER);
		if (findByDescription.isEmpty()) {
			CreditAccountDTO result = CreditAccountDTO.builder()
				.description(CreditAccount.INCOME_TRANSFER)
				.category(this.categoryService.incomeTransfer().getDescription())
				.build();
			return insert(result);
		}
		else {
			return toDTO(findByDescription.get());
		}
	}

}
