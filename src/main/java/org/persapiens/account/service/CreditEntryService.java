package org.persapiens.account.service;

import java.math.BigDecimal;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.domain.CreditEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.persistence.CreditEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class CreditEntryService extends EntryService<CreditEntry, EquityAccount, EquityCategory, CreditAccount, CreditCategory> {

	private CreditEntryRepository creditEntryRepository;
	private OwnerService ownerService;
	private EquityAccountService equityAccountService;

	public CreditEntryService(CreditEntryRepository creditEntryRepository, 
			EquityAccountService equityAccountService,
			CreditAccountService creditAccountService, 
			OwnerService ownerService) {
		super(creditEntryRepository, equityAccountService, creditAccountService, ownerService);
		this.creditEntryRepository = creditEntryRepository;
		this.equityAccountService = equityAccountService;
		this.ownerService = ownerService;
	}

	@Override
	protected CreditEntry createEntry() {
		return new CreditEntry();
	}

	public BigDecimal creditSum(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		return this.creditEntryRepository.creditSum(owner, equityAccount).getValue();
	}

}
