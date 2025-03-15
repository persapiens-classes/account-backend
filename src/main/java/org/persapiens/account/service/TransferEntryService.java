package org.persapiens.account.service;

import java.math.BigDecimal;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.TransferEntry;
import org.persapiens.account.persistence.TransferEntryRepository;

import org.springframework.stereotype.Service;

@Service
public class TransferEntryService
		extends EntryService<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> {

	private TransferEntryRepository transferEntryRepository;

	private EquityAccountService equityAccountService;

	private OwnerService ownerService;

	public TransferEntryService(TransferEntryRepository transferEntryRepository,
			EquityAccountService equityAccountRepository, OwnerService ownerService) {
		super(transferEntryRepository, equityAccountRepository, equityAccountRepository, ownerService);
		this.transferEntryRepository = transferEntryRepository;
		this.equityAccountService = equityAccountRepository;
		this.ownerService = ownerService;
	}

	@Override
	protected TransferEntry createEntry() {
		return new TransferEntry();
	}

	public BigDecimal creditSum(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		return this.transferEntryRepository.creditSum(owner, equityAccount);
	}

	public BigDecimal debitSum(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		return this.transferEntryRepository.debitSum(owner, equityAccount);
	}

}
