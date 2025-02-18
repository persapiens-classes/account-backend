package org.persapiens.account.service;

import java.math.BigDecimal;

import org.persapiens.account.domain.Entry;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.CategoryFactory;
import org.persapiens.account.persistence.CreditAccountFactory;
import org.persapiens.account.persistence.DebitAccountFactory;
import org.persapiens.account.persistence.EntryFactory;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntryDTOFactory {

	@Autowired
	private EntryService entryService;

	@Autowired
	private EntryFactory entryFactory;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private CategoryFactory categoryFactory;

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
				this.debitAccountFactory.debitAccount(inAccount.getDescription(),
						this.categoryFactory.category(inAccount.getCategory())),
				this.equityAccountFactory.equityAccount(outAccount.getDescription(),
						this.categoryFactory.category(outAccount.getCategory())),
				value));
	}

	public EntryDTO entryDTO(OwnerDTO owner, EquityAccountDTO inAccount, CreditAccountDTO outAccount,
			BigDecimal value) {
		return entryDTO(this.entryFactory.entry(this.ownerFactory.owner(owner.name()),
				this.equityAccountFactory.equityAccount(inAccount.getDescription(),
						this.categoryFactory.category(inAccount.getCategory())),
				this.creditAccountFactory.creditAccount(outAccount.getDescription(),
						this.categoryFactory.category(outAccount.getCategory())),
				value));
	}

}
