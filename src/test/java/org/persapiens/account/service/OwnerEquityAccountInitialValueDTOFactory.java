package org.persapiens.account.service;

import java.math.BigDecimal;

import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.persistence.EquityCategoryFactory;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.OwnerEquityAccountInitialValueFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerEquityAccountInitialValueDTOFactory {

	@Autowired
	private OwnerEquityAccountInitialValueFactory ownerEquityAccountInitialValueFactory;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private EquityCategoryFactory categoryFactory;

	public OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValueDTO(
			OwnerEquityAccountInitialValue ownerEquityAccountInitialValue) {
		return new OwnerEquityAccountInitialValueDTO(ownerEquityAccountInitialValue.getOwner().getName(),
				ownerEquityAccountInitialValue.getEquityAccount().getDescription(),
				ownerEquityAccountInitialValue.getValue());
	}

	public OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValueDTO(OwnerDTO owner,
			AccountDTO equityAccount, BigDecimal value) {
		return ownerEquityAccountInitialValueDTO(this.ownerEquityAccountInitialValueFactory
			.ownerEquityAccountInitialValue(this.ownerFactory.owner(owner.name()), this.equityAccountFactory
				.equityAccount(equityAccount.description(), this.categoryFactory.category(equityAccount.category())),
					value));
	}

}
