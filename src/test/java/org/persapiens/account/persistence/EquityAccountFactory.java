package org.persapiens.account.persistence;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EquityAccountFactory {

	private EquityAccountRepository equityAccountRepository;

	private EquityCategoryFactory categoryFactory;

	public EquityAccount equityAccount(String description, EquityCategory category) {
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
		return equityAccount(EquityAccountConstants.WALLET, this.categoryFactory.cash());
	}

	public EquityAccount savings() {
		return equityAccount(EquityAccountConstants.SAVINGS, this.categoryFactory.bank());
	}

	public EquityAccount checking() {
		return equityAccount(EquityAccountConstants.CHECKING, this.categoryFactory.cash());
	}

	public EquityAccount investment() {
		return equityAccount(EquityAccountConstants.INVESTMENT, this.categoryFactory.cash());
	}

}
