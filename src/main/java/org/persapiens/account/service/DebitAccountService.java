package org.persapiens.account.service;

import java.util.Optional;

import org.persapiens.account.domain.DebitAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebitAccountService extends AccountService<DebitAccount> {
    @Autowired
    private CategoryService categoryService;
    
    public DebitAccount expenseTransfer() {
        Optional<DebitAccount> findByDescription = findByDescription(DebitAccount.EXPENSE_TRANSFER);
        if (findByDescription.isEmpty()) {
            DebitAccount result = DebitAccount.builder()
                    .description(DebitAccount.EXPENSE_TRANSFER)
                    .category(categoryService.expenseTransfer())
                    .build();
            return save(result);
        } else {
            return findByDescription.get();
        }
    }
}
