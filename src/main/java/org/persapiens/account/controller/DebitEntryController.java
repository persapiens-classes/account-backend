package org.persapiens.account.controller;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.service.EntryService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debitEntries")
public class DebitEntryController
		extends EntryController<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> {

	public DebitEntryController(EntryService<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> debitEntryService) {
		super(debitEntryService);
	}

}
