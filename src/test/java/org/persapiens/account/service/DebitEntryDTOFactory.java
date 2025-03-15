package org.persapiens.account.service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.DebitAccountFactory;
import org.persapiens.account.persistence.DebitCategoryFactory;
import org.persapiens.account.persistence.DebitEntryFactory;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.EquityCategoryFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DebitEntryDTOFactory {

	private DebitEntryService debitEntryService;

	private DebitEntryFactory debitEntryFactory;

	private OwnerFactory ownerFactory;

	private DebitCategoryFactory debitCategoryFactory;

	private EquityCategoryFactory equityCategoryFactory;

	private DebitAccountFactory debitAccountFactory;

	private EquityAccountFactory equityAccountFactory;

	public EntryDTO debitEntryDTO(DebitEntry entry) {
		return this.debitEntryService.toDTO(entry);
	}

	public EntryDTO debitEntryDTO(OwnerDTO owner, AccountDTO inAccount, AccountDTO outAccount, BigDecimal value) {
		return debitEntryDTO(this.debitEntryFactory.entry(this.ownerFactory.owner(owner.name()),
				this.debitAccountFactory.debitAccount(inAccount.description(),
						this.debitCategoryFactory.category(inAccount.category())),
				this.equityAccountFactory.equityAccount(outAccount.description(),
						this.equityCategoryFactory.category(outAccount.category())),
				value));
	}

}
