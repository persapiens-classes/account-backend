package org.persapiens.account.service;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.domain.CreditEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.persistence.CreditEntryRepository;

import org.springframework.stereotype.Service;

@Service
public class CreditEntryService
		extends EntryService<CreditEntry, EquityAccount, EquityCategory, CreditAccount, CreditCategory> {

	public CreditEntryService(CreditEntryRepository creditEntryRepository, EquityAccountService equityAccountRepository,
			CreditAccountService creditAccountRepository, OwnerService ownerService) {
		super(creditEntryRepository, equityAccountRepository, creditAccountRepository, ownerService);
	}

	@Override
	protected CreditEntry createEntry() {
		return new CreditEntry();
	}

}
