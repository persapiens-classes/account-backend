package org.persapiens.account.controller;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.service.EquityAccountService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equityAccounts")
public class EquityAccountController extends AccountController<EquityAccountDTO, EquityAccount> {

	public EquityAccountController(EquityAccountService equityAccountService) {
		super(equityAccountService);
	}

}
