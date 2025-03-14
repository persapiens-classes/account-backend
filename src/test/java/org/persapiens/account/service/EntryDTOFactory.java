package org.persapiens.account.service;

import java.math.BigDecimal;

import org.persapiens.account.domain.CreditEntry;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.EquityCategoryFactory;
import org.persapiens.account.persistence.CreditAccountFactory;
import org.persapiens.account.persistence.CreditCategoryFactory;
import org.persapiens.account.persistence.CreditEntryFactory;
import org.persapiens.account.persistence.DebitAccountFactory;
import org.persapiens.account.persistence.DebitCategoryFactory;
import org.persapiens.account.persistence.DebitEntryFactory;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntryDTOFactory {

	@Autowired
	private DebitEntryService debitEntryService;

	@Autowired
	private CreditEntryService creditEntryService;

	@Autowired
	private DebitEntryFactory debitEntryFactory;

	@Autowired
	private CreditEntryFactory creditEntryFactory;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private CreditCategoryFactory creditCategoryFactory;

	@Autowired
	private DebitCategoryFactory debitCategoryFactory;

	@Autowired
	private EquityCategoryFactory equityCategoryFactory;

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	public EntryDTO debitEntryDTO(DebitEntry entry) {
		return this.debitEntryService.toDTO(entry);
	}

	public EntryDTO creditEntryDTO(CreditEntry entry) {
		return this.creditEntryService.toDTO(entry);
	}

	public EntryDTO debitEntryDTO(OwnerDTO owner, AccountDTO inAccount, AccountDTO outAccount, BigDecimal value) {
		return debitEntryDTO(this.debitEntryFactory.entry(this.ownerFactory.owner(owner.name()),
				this.debitAccountFactory.debitAccount(inAccount.description(),
						this.debitCategoryFactory.category(inAccount.category())),
				this.equityAccountFactory.equityAccount(outAccount.description(),
						this.equityCategoryFactory.category(outAccount.category())),
				value));
	}

	public EntryDTO creditEntryDTO(OwnerDTO owner, AccountDTO inAccount, AccountDTO outAccount,
			BigDecimal value) {
		return creditEntryDTO(this.creditEntryFactory.entry(this.ownerFactory.owner(owner.name()),
				this.equityAccountFactory.equityAccount(inAccount.description(),
						this.equityCategoryFactory.category(inAccount.category())),
				this.creditAccountFactory.creditAccount(outAccount.description(),
						this.creditCategoryFactory.category(outAccount.category())),
				value));
	}

}
