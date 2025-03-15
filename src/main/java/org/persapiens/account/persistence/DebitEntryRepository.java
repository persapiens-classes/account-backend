package org.persapiens.account.persistence;

import java.math.BigDecimal;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;

import org.springframework.data.jpa.repository.Query;

public interface DebitEntryRepository
		extends EntryRepository<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> {

	@Query("SELECT COALESCE(SUM(e.value), 0) FROM DebitEntry e WHERE e.outOwner = ?1 and e.outAccount = ?2")
	BigDecimal debitSum(Owner owner, EquityAccount equityAccount);

}
