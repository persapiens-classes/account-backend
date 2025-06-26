package org.persapiens.account.controller;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.TransferEntry;
import org.persapiens.account.service.EntryService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transferEntries")
public class TransferEntryController
		extends EntryController<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> {

	public TransferEntryController(EntryService<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> transferEntryService) {
		super(transferEntryService);
	}

}
