package org.persapiens.account.service;

import java.math.BigDecimal;

import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.persistence.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntryService extends CrudService<Entry, Long> {
    @Autowired
    private EntryRepository entryRepository;
    
    @Override
    @Transactional
    public Entry save(Entry entry) {
        entry.verifyAttributes();

        return super.save(entry);
    }
    
    public BigDecimal creditSum(Owner owner, EquityAccount equityAccount) {
        return entryRepository.creditSum(owner, equityAccount).getValue();
    }
    
    public BigDecimal debitSum(Owner owner, EquityAccount equityAccount) {
        return entryRepository.debitSum(owner, equityAccount).getValue();
    }
}
