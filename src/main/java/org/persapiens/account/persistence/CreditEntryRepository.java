package org.persapiens.account.persistence;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.domain.CreditEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;

import org.springframework.data.jpa.repository.Query;

public interface CreditEntryRepository
		extends EntryRepository<CreditEntry, EquityAccount, EquityCategory, CreditAccount, CreditCategory> {

	@Query("SELECT SUM(e.value) as value FROM Entry e WHERE e.ownerIn = ?1 and e.inAccount = ?2")
	EntrySum creditSum(Owner owner, EquityAccount equityAccount);

}
