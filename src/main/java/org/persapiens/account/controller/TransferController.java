package org.persapiens.account.controller;

import org.persapiens.account.service.TransferService;
import org.persapiens.account.dto.TransferDTO;
import org.persapiens.account.service.EquityAccountService;
import org.persapiens.account.service.OwnerService;

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
        transferService.transfer(transferDTO.getValue(),
            ownerService.findByName(transferDTO.getDebitOwner()).get(),
            equityAccountService.findByDescription(transferDTO.getDebitAccount()).get(),
            ownerService.findByName(transferDTO.getCreditOwner()).get(),
            equityAccountService.findByDescription(transferDTO.getCreditAccount()).get());
    }

}