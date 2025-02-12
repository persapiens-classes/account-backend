package org.persapiens.account.service;

import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.persistence.CategoryFactory;
import org.persapiens.account.persistence.EquityAccountFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquityAccountDTOFactory {

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private CategoryFactory categoryFactory;

	public EquityAccountDTO equityAccountDTO(EquityAccount equityAccount) {
		return EquityAccountDTO.builder()
			.description(equityAccount.getDescription())
			.category(equityAccount.getCategory().getDescription())
			.build();
	}

	public EquityAccountDTO equityAccountDTO(String description, String category) {
		return equityAccountDTO(
				this.equityAccountFactory.equityAccount(description, this.categoryFactory.category(category)));
	}

	public EquityAccountDTO wallet() {
		return equityAccountDTO(EquityAccountConstants.WALLET, this.categoryFactory.cash().getDescription());
	}

	public EquityAccountDTO savings() {
		return equityAccountDTO(EquityAccountConstants.SAVINGS, this.categoryFactory.bank().getDescription());
	}

	public EquityAccountDTO checking() {
		return equityAccountDTO(EquityAccountConstants.CHECKING, this.categoryFactory.cash().getDescription());
	}

	public EquityAccountDTO investiment() {
		return equityAccountDTO(EquityAccountConstants.INVESTIMENT, this.categoryFactory.cash().getDescription());
	}

}
