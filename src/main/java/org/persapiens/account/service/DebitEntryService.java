package org.persapiens.account.service;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.persistence.DebitEntryRepository;

import org.springframework.stereotype.Service;

@Service
public class DebitEntryService
		extends EntryService<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> {

	public DebitEntryService(DebitEntryRepository debitEntryRepository, DebitAccountService debitAccountRepository,
			EquityAccountService equityAccountRepository, OwnerService ownerService) {
		super(debitEntryRepository, debitAccountRepository, equityAccountRepository, ownerService);
	}

	@Override
	protected DebitEntry createEntry() {
		return new DebitEntry();
	}

}
