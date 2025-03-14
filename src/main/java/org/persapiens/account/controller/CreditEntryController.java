package org.persapiens.account.controller;

import java.math.BigDecimal;

import org.persapiens.account.domain.CreditEntry;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.service.CreditEntryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creditEntries")
public class CreditEntryController extends EntryController<CreditEntry, EquityAccount, EquityCategory, CreditAccount, CreditCategory> {

	private CreditEntryService creditEntryService;

	public CreditEntryController(CreditEntryService creditEntryService) {
		super(creditEntryService);
		this.creditEntryService = creditEntryService;
	}

	@GetMapping("/creditSum")
	public BigDecimal creditSum(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.creditEntryService.creditSum(owner, equityAccount);
	}

}
