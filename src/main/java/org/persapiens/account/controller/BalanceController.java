package org.persapiens.account.controller;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.persapiens.account.service.BalanceService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class BalanceController {

	private BalanceService balanceService;

	@GetMapping("/balance")
	public BigDecimal balance(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.balanceService.balance(owner, equityAccount);
	}

}
