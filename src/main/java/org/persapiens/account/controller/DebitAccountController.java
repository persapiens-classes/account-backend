package org.persapiens.account.controller;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.service.DebitAccountService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debitAccounts")
public class DebitAccountController extends AccountController<DebitAccountDTO, DebitAccount> {

	public DebitAccountController(DebitAccountService debitAccountService) {
		super(debitAccountService);
	}

}
