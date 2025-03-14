package org.persapiens.account.controller;

import java.math.BigDecimal;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.TransferEntry;
import org.persapiens.account.service.TransferEntryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transferEntries")
public class TransferEntryController
		extends EntryController<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> {

	private TransferEntryService transferEntryService;

	public TransferEntryController(TransferEntryService transferEntryService) {
		super(transferEntryService);
		this.transferEntryService = transferEntryService;
	}

	@GetMapping("/creditSum")
	public BigDecimal creditSum(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.transferEntryService.creditSum(owner, equityAccount);
	}

	@GetMapping("/debitSum")
	public BigDecimal debitSum(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.transferEntryService.debitSum(owner, equityAccount);
	}

}
