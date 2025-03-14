package org.persapiens.account.controller;

import org.persapiens.account.domain.DebitEntry;

import java.math.BigDecimal;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.service.DebitEntryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debitEntries")
public class DebitEntryController extends EntryController<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> {

	private DebitEntryService debitEntryService;

	public DebitEntryController(DebitEntryService debitEntryService) {
		super(debitEntryService);
		this.debitEntryService = debitEntryService;
	}

	@GetMapping("/debitSum")
	public BigDecimal debitSum(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.debitEntryService.debitSum(owner, equityAccount);
	}

}
