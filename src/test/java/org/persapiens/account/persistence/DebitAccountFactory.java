package org.persapiens.account.persistence;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.common.DebitAccountConstants;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DebitAccountFactory {

	private DebitAccountRepository debitAccountRepository;

	private DebitCategoryFactory categoryFactory;

	public DebitAccount debitAccount(String description, DebitCategory category) {
		Optional<DebitAccount> findByDescription = this.debitAccountRepository.findByDescription(description);
		if (findByDescription.isEmpty()) {
			DebitAccount debitAccount = DebitAccount.builder().description(description).category(category).build();
			return this.debitAccountRepository.save(debitAccount);
		}
		else {
			return findByDescription.get();
		}
	}

	public DebitAccount gasoline() {
		return debitAccount(DebitAccountConstants.GASOLINE, this.categoryFactory.transport());
	}

}
