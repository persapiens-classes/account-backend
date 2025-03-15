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
public class DebitEntryService
		extends EntryService<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> {

	private DebitEntryRepository debitEntryRepository;

	private EquityAccountService equityAccountService;

	private OwnerService ownerService;

	public DebitEntryService(DebitEntryRepository debitEntryRepository, DebitAccountService debitAccountRepository,
			EquityAccountService equityAccountRepository, OwnerService ownerService) {
		super(debitEntryRepository, debitAccountRepository, equityAccountRepository, ownerService);
		this.debitEntryRepository = debitEntryRepository;
		this.equityAccountService = equityAccountRepository;
		this.ownerService = ownerService;
	}

	@Override
	protected DebitEntry createEntry() {
		return new DebitEntry();
	}

	public BigDecimal debitSum(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		return this.debitEntryRepository.debitSum(owner, equityAccount);
	}

}
