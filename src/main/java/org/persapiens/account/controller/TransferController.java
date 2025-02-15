package org.persapiens.account.controller;

import lombok.AllArgsConstructor;
import org.persapiens.account.dto.TransferDTO;
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

	@PostMapping("/transfer")
	public void transfer(@RequestBody(required = true) TransferDTO transferDTO) {
		this.transferService.transfer(transferDTO);
	}

}
