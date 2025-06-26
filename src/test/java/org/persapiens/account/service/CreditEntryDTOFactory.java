package org.persapiens.account.service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.domain.CreditEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.CreditAccountFactory;
import org.persapiens.account.persistence.CreditCategoryFactory;
import org.persapiens.account.persistence.CreditEntryFactory;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.EquityCategoryFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreditEntryDTOFactory {

	private EntryService<CreditEntry, EquityAccount, EquityCategory, CreditAccount, CreditCategory> creditEntryService;

	private CreditEntryFactory creditEntryFactory;

	private OwnerFactory ownerFactory;

	private CreditCategoryFactory creditCategoryFactory;

	private EquityCategoryFactory equityCategoryFactory;

	private CreditAccountFactory creditAccountFactory;

	private EquityAccountFactory equityAccountFactory;

	public EntryDTO creditEntryDTO(CreditEntry entry) {
		return this.creditEntryService.toDTO(entry);
	}

	public EntryDTO creditEntryDTO(OwnerDTO owner, AccountDTO inAccount, AccountDTO outAccount, BigDecimal value) {
		return creditEntryDTO(this.creditEntryFactory.entry(this.ownerFactory.owner(owner.name()),
				this.equityAccountFactory.equityAccount(inAccount.description(),
						this.equityCategoryFactory.category(inAccount.category())),
				this.creditAccountFactory.creditAccount(outAccount.description(),
						this.creditCategoryFactory.category(outAccount.category())),
				value));
	}

}
