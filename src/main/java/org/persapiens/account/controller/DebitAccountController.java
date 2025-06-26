package org.persapiens.account.controller;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.service.AccountService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debitAccounts")
public class DebitAccountController extends AccountController<DebitAccount, DebitCategory> {

	public DebitAccountController(AccountService<DebitAccount, DebitCategory> debitAccountService) {
		super(debitAccountService);
	}

}
