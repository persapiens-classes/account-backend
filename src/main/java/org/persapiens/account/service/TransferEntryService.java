package org.persapiens.account.service;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.TransferEntry;
import org.persapiens.account.persistence.TransferEntryRepository;

import org.springframework.stereotype.Service;

@Service
public class TransferEntryService
		extends EntryService<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> {

	public TransferEntryService(TransferEntryRepository transferEntryRepository,
			EquityAccountService equityAccountRepository, OwnerService ownerService) {
		super(transferEntryRepository, equityAccountRepository, equityAccountRepository, ownerService);
	}

	@Override
	protected TransferEntry createEntry() {
		return new TransferEntry();
	}

}
