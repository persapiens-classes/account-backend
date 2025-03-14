package org.persapiens.account.service;

import java.math.BigDecimal;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.TransferEntry;
import org.persapiens.account.persistence.TransferEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferEntryService extends EntryService<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> {

	private TransferEntryRepository transferEntryRepository;
	private OwnerService ownerService;
	private EquityAccountService equityAccountService;

	public TransferEntryService(TransferEntryRepository transferEntryRepository, 
			EquityAccountService equityAccountService,
			OwnerService ownerService) {
		super(transferEntryRepository, equityAccountService, equityAccountService, ownerService);
		this.transferEntryRepository = transferEntryRepository;
		this.equityAccountService = equityAccountService;
		this.ownerService = ownerService;
	}

	@Override
	protected TransferEntry createEntry() {
		return new TransferEntry();
	}

	public BigDecimal debitSum(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		return this.transferEntryRepository.debitSum(owner, equityAccount).getValue();
	}

	public BigDecimal creditSum(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		return this.transferEntryRepository.creditSum(owner, equityAccount).getValue();
	}

}
