package org.persapiens.account.controller;

import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.service.AccountService;
import org.persapiens.account.service.EntryService;
import org.persapiens.account.service.EquityAccountService;
import org.persapiens.account.service.OwnerService;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entry")
public class EntryController extends CrudController<EntryDTO, Entry, Long> {

    @Autowired
    private EntryService entryService;
    
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private EquityAccountService equityAccountService;

    @Autowired
    private AccountService<Account> accountService;

    @Override
    protected Entry toEntity(EntryDTO dto) {
        return Entry.builder()
            .inAccount(accountService.findByDescription(dto.getInAccount().getDescription()).get())
            .outAccount(accountService.findByDescription(dto.getOutAccount().getDescription()).get())
            .owner(ownerService.findByName(dto.getOwner().getName()).get())
            .date(dto.getDate())
            .value(dto.getValue())
            .note(dto.getNote())
            .build();
    }

    @Override
    protected EntryDTO toDTO(Entry entity) {
        return EntryDTO.builder()
            .inAccount(AccountDTO.builder()
                .description(entity.getInAccount().getDescription())
                .category(CategoryDTO.builder().description(
                    entity.getInAccount().getCategory().getDescription()).build())
                .build())
            .outAccount(AccountDTO.builder()
                .description(entity.getOutAccount().getDescription())
                .category(CategoryDTO.builder().description(
                    entity.getOutAccount().getCategory().getDescription()).build())
                .build())
            .owner(OwnerDTO.builder().name(entity.getOwner().getName()).build())
            .date(entity.getDate())
            .value(entity.getValue())
            .note(entity.getNote())
            .build();
    }

    @GetMapping("/creditSum")
    public BigDecimal creditSum(String owner, String equityAccount) {
        return entryService.creditSum(ownerService.findByName(owner).get(), 
            equityAccountService.findByDescription(equityAccount).get());
    }
    
    @GetMapping("/debitSum")
    public BigDecimal debitSum(String owner, String equityAccount) {
        return entryService.debitSum(ownerService.findByName(owner).get(), 
            equityAccountService.findByDescription(equityAccount).get());
    }
}