package org.persapiens.account.persistence;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;
import org.springframework.data.jpa.repository.Query;

public interface DebitEntryRepository extends EntryRepository<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> {

	@Query("SELECT SUM(e.value) as value FROM Entry e WHERE e.outOwner = ?1 and e.outAccount = ?2")
	EntrySum debitSum(Owner owner, EquityAccount equityAccount);

}
