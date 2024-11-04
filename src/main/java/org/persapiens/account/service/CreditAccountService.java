package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.CreditAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditAccountService extends AccountService<CreditAccount> {
    @Autowired
    private CategoryService categoryService;
    
    public CreditAccount incomeTransfer() {
        Optional<CreditAccount> findByDescription = findByDescription(CreditAccount.INCOME_TRANSFER);
        if (findByDescription.isEmpty()) {
            CreditAccount result = CreditAccount.builder()
                    .description(CreditAccount.INCOME_TRANSFER)
                    .category(categoryService.incomeTransfer())
                    .build();
            return save(result);
        } else {
            return findByDescription.get();
        }
    }
}
