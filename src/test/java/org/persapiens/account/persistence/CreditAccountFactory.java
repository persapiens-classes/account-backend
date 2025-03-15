package org.persapiens.account.persistence;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditCategory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreditAccountFactory {

	private CreditAccountRepository creditAccountRepository;

	private CreditCategoryFactory categoryFactory;

	public CreditAccount creditAccount(String description, CreditCategory category) {
		Optional<CreditAccount> findByDescription = this.creditAccountRepository.findByDescription(description);
		if (findByDescription.isEmpty()) {
			CreditAccount creditAccount = CreditAccount.builder().description(description).category(category).build();
			return this.creditAccountRepository.save(creditAccount);
		}
		else {
			return findByDescription.get();
		}
	}

	public CreditAccount internship() {
		return creditAccount(CreditAccountConstants.INTERNSHIP, this.categoryFactory.salary());
	}

}
