package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.persistence.CreditAccountRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditAccountService extends AccountService<CreditAccountDTO, CreditAccount> {

	private CreditAccountRepository creditAccountRepository;

	private CategoryService categoryService;

	public CreditAccountService(CreditAccountRepository creditAccountRepository, CategoryService categoryService) {
		super(creditAccountRepository, categoryService);
		this.creditAccountRepository = creditAccountRepository;
		this.categoryService = categoryService;
	}

	@Override
	protected CreditAccount createAccount() {
		return new CreditAccount();
	}

	@Override
	protected CreditAccountDTO createAccountDTO(String description, String category) {
		return new CreditAccountDTO(description, category);
	}

	@Transactional
	public CreditAccountDTO incomeTransfer() {
		Optional<CreditAccount> findByDescription = this.creditAccountRepository
			.findByDescription(CreditAccount.INCOME_TRANSFER);
		if (findByDescription.isEmpty()) {
			CreditAccount result = CreditAccount.builder()
				.description(CreditAccount.INCOME_TRANSFER)
				.category(this.categoryService.incomeTransfer())
				.build();
			return toDTO(this.creditAccountRepository.save(result));
		}
		else {
			return toDTO(findByDescription.get());
		}
	}

}
