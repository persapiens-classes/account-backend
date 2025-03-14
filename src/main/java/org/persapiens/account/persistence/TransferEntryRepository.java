package org.persapiens.account.persistence;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.TransferEntry;

import org.springframework.data.jpa.repository.Query;

public interface TransferEntryRepository
		extends EntryRepository<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> {

	@Query("SELECT SUM(e.value) as value FROM Entry e WHERE e.ownerIn = ?1 and e.inAccount = ?2")
	EntrySum creditSum(Owner owner, EquityAccount equityAccount);

	@Query("SELECT SUM(e.value) as value FROM Entry e WHERE e.ownerOut = ?1 and e.outAccount = ?2")
	EntrySum debitSum(Owner owner, EquityAccount equityAccount);

}
