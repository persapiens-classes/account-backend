package org.persapiens.account.controller;

import org.persapiens.account.dto.TransferDTO;
import org.persapiens.account.service.EquityAccountService;
import org.persapiens.account.service.OwnerService;
import org.persapiens.account.service.TransferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TransferController {

	@Autowired
	private TransferService transferService;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private EquityAccountService equityAccountService;

	@PostMapping("/transfer")
	public void transfer(@RequestBody TransferDTO transferDTO) {
		this.transferService.transfer(transferDTO.getValue(),
				this.ownerService.findByName(transferDTO.getDebitOwner()).get(),
				this.equityAccountService.findByDescription(transferDTO.getDebitAccount()).get(),
				this.ownerService.findByName(transferDTO.getCreditOwner()).get(),
				this.equityAccountService.findByDescription(transferDTO.getCreditAccount()).get());
	}

}
