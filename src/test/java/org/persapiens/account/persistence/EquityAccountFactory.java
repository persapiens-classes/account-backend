package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.EquityAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquityAccountFactory {

	@Autowired
	private EquityAccountRepository equityAccountRepository;

	@Autowired
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
