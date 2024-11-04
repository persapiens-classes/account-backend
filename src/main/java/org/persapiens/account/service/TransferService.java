package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.domain.EquityAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    private final EntryService entryService;

    private final DebitAccountService debitAccountService;

    private final CreditAccountService creditAccountService;
    
    @Autowired
    public TransferService(EntryService entryService,
            DebitAccountService debitAccountService, CreditAccountService creditAccountService) {
        super();
        this.entryService = entryService;
        this.debitAccountService = debitAccountService;
        this.creditAccountService = creditAccountService;
    }
    
    @Transactional
    public void transfer(BigDecimal value, 
            Owner ownerDebito, EquityAccount debitEquityAccount, 
            Owner ownerCredito, EquityAccount creditEquityAccount) {

        DebitAccount debitAccount = debitAccountService.expenseTransfer();
        CreditAccount creditAccount = creditAccountService.incomeTransfer();
        
        if (ownerCredito.equals(ownerDebito)) {
            throw new IllegalArgumentException("Owners should be different: "
                    + ownerDebito + " = " + ownerCredito);
        }

        LocalDateTime date = LocalDateTime.now();

        Entry debitEntry = Entry.builder()
                .inAccount(debitAccount)
                .outAccount(debitEquityAccount)
                .owner(ownerDebito)
                .value(value)
                .date(date)
                .note("Transfer debit entry")
                .build();
        entryService.save(debitEntry);

        Entry creditEntry = Entry.builder()
                .inAccount(creditEquityAccount)
                .outAccount(creditAccount)
                .owner(ownerCredito)
                .value(value)
                .date(date)
                .note("Transfer credit entry")
                .build();
        entryService.save(creditEntry);
    }
}
