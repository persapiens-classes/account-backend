package org.persapiens.account.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import org.persapiens.account.dto.BalanceDTO;
import org.persapiens.account.service.BalanceService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/balances")
public class BalanceController {

	private BalanceService balanceService;

	@FindOneBeanDoc
	@GetMapping("/filter")
	public BalanceDTO balanceByOwnerAndEquityAccount(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.balanceService.balanceByOwnerAndEquityAccount(owner, equityAccount);
	}

	@FindAllBeanDoc
	@GetMapping
	public List<BalanceDTO> balanceAll() {
		return this.balanceService.balanceAll();
	}

}
