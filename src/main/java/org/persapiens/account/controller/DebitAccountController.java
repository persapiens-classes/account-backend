package org.persapiens.account.controller;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.service.DebitAccountService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debitAccounts")
public class DebitAccountController extends AccountController<DebitAccount, DebitCategory> {

	public DebitAccountController(DebitAccountService debitAccountService) {
		super(debitAccountService);
	}

}
