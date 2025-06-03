package org.persapiens.account.service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.EquityCategoryFactory;
import org.persapiens.account.persistence.OwnerEquityAccountInitialValueFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OwnerEquityAccountInitialValueDTOFactory {

	private OwnerEquityAccountInitialValueFactory ownerEquityAccountInitialValueFactory;

	private OwnerFactory ownerFactory;

	private EquityAccountFactory equityAccountFactory;

	private EquityCategoryFactory categoryFactory;

	public OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValueDTO(
			OwnerEquityAccountInitialValue ownerEquityAccountInitialValue) {
		return new OwnerEquityAccountInitialValueDTO(ownerEquityAccountInitialValue.getOwner().getName(),
				new AccountDTO(ownerEquityAccountInitialValue.getEquityAccount().getDescription(),
						ownerEquityAccountInitialValue.getEquityAccount().getCategory().getDescription()),
				ownerEquityAccountInitialValue.getInitialValue());
	}

	public OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValueDTO(OwnerDTO owner, AccountDTO equityAccount,
			BigDecimal value) {
		return ownerEquityAccountInitialValueDTO(this.ownerEquityAccountInitialValueFactory
			.ownerEquityAccountInitialValue(this.ownerFactory.owner(owner.name()), this.equityAccountFactory
				.equityAccount(equityAccount.description(), this.categoryFactory.category(equityAccount.category())),
					value));
	}

}
