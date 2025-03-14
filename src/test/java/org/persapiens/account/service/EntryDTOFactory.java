package org.persapiens.account.service;

import java.math.BigDecimal;

import org.persapiens.account.domain.Entry;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.EquityCategoryFactory;
import org.persapiens.account.persistence.CreditAccountFactory;
import org.persapiens.account.persistence.DebitAccountFactory;
import org.persapiens.account.persistence.DebitEntryFactory;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntryDTOFactory {

	@Autowired
	private EntryService entryService;

	@Autowired
	private DebitEntryFactory entryFactory;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private EquityCategoryFactory categoryFactory;

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	public EntryDTO entryDTO(Entry entry) {
		return this.entryService.toDTO(entry);
	}

	public EntryDTO entryDTO(OwnerDTO owner, DebitAccountDTO inAccount, EquityAccountDTO outAccount, BigDecimal value) {
		return entryDTO(this.entryFactory.entry(this.ownerFactory.owner(owner.name()),
				this.debitAccountFactory.debitAccount(inAccount.description(),
						this.categoryFactory.category(inAccount.category())),
				this.equityAccountFactory.equityAccount(outAccount.description(),
						this.categoryFactory.category(outAccount.category())),
				value));
	}

	public EntryDTO entryDTO(OwnerDTO owner, EquityAccountDTO inAccount, CreditAccountDTO outAccount,
			BigDecimal value) {
		return entryDTO(this.entryFactory.entry(this.ownerFactory.owner(owner.name()),
				this.equityAccountFactory.equityAccount(inAccount.description(),
						this.categoryFactory.category(inAccount.category())),
				this.creditAccountFactory.creditAccount(outAccount.description(),
						this.categoryFactory.category(outAccount.category())),
				value));
	}

}
