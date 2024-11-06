package org.persapiens.account.persistence;

import java.util.Optional;

import static org.persapiens.account.common.CreditAccountConstants.INTERNSHIP;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.CreditAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditAccountFactory {

	@Autowired
	private CreditAccountRepository creditAccountRepository;

	@Autowired
	private CategoryFactory categoryFactory;

	public CreditAccount creditAccount(String description, Category category) {
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
		return creditAccount(INTERNSHIP, this.categoryFactory.salary());
	}

}
