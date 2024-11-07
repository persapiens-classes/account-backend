package org.persapiens.account.controller;

import lombok.AllArgsConstructor;
import org.persapiens.account.dto.TransferDTO;
import org.persapiens.account.service.EquityAccountService;
import org.persapiens.account.service.OwnerService;
import org.persapiens.account.service.TransferService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TransferController {

	private TransferService transferService;

	private OwnerService ownerService;

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
