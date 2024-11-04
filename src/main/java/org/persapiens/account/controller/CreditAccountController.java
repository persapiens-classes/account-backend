package org.persapiens.account.controller;

import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.service.CategoryService;
import org.persapiens.account.service.CreditAccountService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creditAccount")
public class CreditAccountController extends CrudController<CreditAccountDTO, CreditAccount, Long> {

    @Autowired
    private CreditAccountService creditAccountService;
    
    @Autowired
    private CategoryService categoryService;

    @Override
    protected CreditAccount toEntity(CreditAccountDTO dto) {
        return CreditAccount.builder()
           .description(dto.getDescription())
           .category(categoryService.findByDescription(dto.getCategory().getDescription()).get())
           .build();
    }

    @Override
    protected CreditAccountDTO toDTO(CreditAccount entity) {
        return CreditAccountDTO.builder()
           .description(entity.getDescription())
           .build();
    }
    
    @GetMapping("/findByDescription")
    public Optional<CreditAccountDTO> findByDescription(@RequestParam String description) {
        return toDTOOptional(creditAccountService.findByDescription(description));
    }

}