package org.persapiens.account.persistence;

import java.util.Optional;

import static org.persapiens.account.common.EquityAccountConstants.SAVINGS;
import static org.persapiens.account.common.EquityAccountConstants.WALLET;
import static org.persapiens.account.common.EquityAccountConstants.INVESTIMENT;
import static org.persapiens.account.common.EquityAccountConstants.CHECKING;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.EquityAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquityAccountFactory {

	@Autowired
	private EquityAccountRepository equityAccountRepository;

	@Autowired
	private CategoryFactory categoryFactory;

	public EquityAccount equityAccount(String description, Category category) {
		Optional<EquityAccount> findByDescription = this.equityAccountRepository.findByDescription(description);
		if (findByDescription.isEmpty()) {
			EquityAccount equityAccount = EquityAccount.builder().description(description).category(category).build();
			return this.equityAccountRepository.save(equityAccount);
		}
		else {
			return findByDescription.get();
		}
	}

	public EquityAccount wallet() {
		return equityAccount(WALLET, this.categoryFactory.cash());
	}

	public EquityAccount savings() {
		return equityAccount(SAVINGS, this.categoryFactory.bank());
	}

	public EquityAccount checking() {
		return equityAccount(CHECKING, this.categoryFactory.cash());
	}

	public EquityAccount investiment() {
		return equityAccount(INVESTIMENT, this.categoryFactory.cash());
	}

}
