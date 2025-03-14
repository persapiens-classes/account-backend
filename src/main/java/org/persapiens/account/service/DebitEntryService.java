package org.persapiens.account.service;

import java.math.BigDecimal;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.persistence.DebitEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class DebitEntryService extends EntryService<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> {

	private DebitEntryRepository debitEntryRepository;
	private OwnerService ownerService;
	private EquityAccountService equityAccountService;

	public DebitEntryService(DebitEntryRepository debitEntryRepository, 
			DebitAccountService debitAccountService, 
			EquityAccountService equityAccountService,
			OwnerService ownerService) {
		super(debitEntryRepository, debitAccountService, equityAccountService, ownerService);
		this.debitEntryRepository = debitEntryRepository;
		this.equityAccountService = equityAccountService;
		this.ownerService = ownerService;
	}

	@Override
	protected DebitEntry createEntry() {
		return new DebitEntry();
	}

	public BigDecimal debitSum(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		return this.debitEntryRepository.debitSum(owner, equityAccount).getValue();
	}

}
