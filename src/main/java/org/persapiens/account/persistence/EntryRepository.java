package org.persapiens.account.persistence;

import org.persapiens.account.domain.Entry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Long> {
    @Query("SELECT SUM(e.value) as value FROM Entry e WHERE e.owner = ?1 and e.inAccount = ?2")
    EntrySum creditSum(Owner owner, EquityAccount equityAccount);

    @Query("SELECT SUM(e.value) as value FROM Entry e WHERE e.owner = ?1 and e.outAccount = ?2")
    EntrySum debitSum(Owner owner, EquityAccount equityAccount);
}
