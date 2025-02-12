package org.persapiens.account.controller;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.service.CreditAccountService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creditAccounts")
public class CreditAccountController extends AccountController<CreditAccountDTO, CreditAccount> {

	public CreditAccountController(CreditAccountService creditAccountService) {
		super(creditAccountService);
	}

}
