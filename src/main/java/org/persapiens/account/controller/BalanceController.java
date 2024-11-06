package org.persapiens.account.controller;

import org.persapiens.account.service.BalanceService;
import org.persapiens.account.service.EquityAccountService;
import org.persapiens.account.service.OwnerService;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BalanceController {

	@Autowired
	private BalanceService balanceService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private EquityAccountService equityAccountService;

	@GetMapping("/balance")
	public BigDecimal balance(@RequestParam String owner, @RequestParam String equityAccount) {
		return balanceService.balance(ownerService.findByName(owner).get(),
				equityAccountService.findByDescription(equityAccount).get());
	}

}