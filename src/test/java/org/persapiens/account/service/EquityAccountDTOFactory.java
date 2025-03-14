package org.persapiens.account.service;

import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.EquityCategoryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquityAccountDTOFactory {

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private EquityCategoryFactory categoryFactory;

	public AccountDTO equityAccountDTO(EquityAccount equityAccount) {
		return new AccountDTO(equityAccount.getDescription(), equityAccount.getCategory().getDescription());
	}

	public AccountDTO equityAccountDTO(String description, String category) {
		return equityAccountDTO(
				this.equityAccountFactory.equityAccount(description, this.categoryFactory.category(category)));
	}

	public AccountDTO wallet() {
		return equityAccountDTO(EquityAccountConstants.WALLET, this.categoryFactory.cash().getDescription());
	}

	public AccountDTO savings() {
		return equityAccountDTO(EquityAccountConstants.SAVINGS, this.categoryFactory.bank().getDescription());
	}

	public AccountDTO checking() {
		return equityAccountDTO(EquityAccountConstants.CHECKING, this.categoryFactory.cash().getDescription());
	}

	public AccountDTO investment() {
		return equityAccountDTO(EquityAccountConstants.INVESTMENT, this.categoryFactory.cash().getDescription());
	}

}
