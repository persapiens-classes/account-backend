package org.persapiens.account.controller;

import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.service.EquityAccountService;
import org.persapiens.account.service.OwnerEquityAccountInitialValueService;
import org.persapiens.account.service.OwnerService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ownerEquityAccountInitialValue")
public class OwnerEquityAccountInitialValueController extends CrudController<OwnerEquityAccountInitialValueDTO, OwnerEquityAccountInitialValue, Long> {

    @Autowired
    private OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService;

    @Autowired
    private OwnerService ownerService;
    
    @Autowired
    private EquityAccountService equityAccountService;

    @Override
    protected OwnerEquityAccountInitialValue toEntity(OwnerEquityAccountInitialValueDTO dto) {
        return OwnerEquityAccountInitialValue.builder()
            .value(dto.getValue())
            .owner(ownerService.findByName(dto.getOwner().getName()).get())
            .equityAccount(equityAccountService.findByDescription(dto.getEquityAccount().getDescription()).get())
            .build();
    }

    @Override
    protected OwnerEquityAccountInitialValueDTO toDTO(OwnerEquityAccountInitialValue entity) {
        return OwnerEquityAccountInitialValueDTO.builder()
            .value(entity.getValue())
            .owner(OwnerDTO.builder().name(entity.getOwner().getName()).build())
            .equityAccount(EquityAccountDTO.builder().description(entity.getEquityAccount().getDescription()).build())
            .build();
    }
    
    @GetMapping("/findByOwnerAndEquityAccount")
    public Optional<OwnerEquityAccountInitialValueDTO> findByOwnerAndEquityAccount(@RequestParam String owner, @RequestParam String equityAccount) {
        return toDTOOptional(ownerEquityAccountInitialValueService.findByOwnerAndEquityAccount(
            ownerService.findByName(owner).get(), equityAccountService.findByDescription(equityAccount).get()));
    }

}