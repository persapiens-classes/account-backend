package org.persapiens.account.persistence;

import java.math.BigDecimal;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.TransferEntry;

import org.springframework.data.jpa.repository.Query;

public interface TransferEntryRepository
		extends EntryRepository<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> {

	@Query("SELECT COALESCE(SUM(e.value), 0) FROM TransferEntry e WHERE e.inOwner = ?1 and e.inAccount = ?2")
	BigDecimal creditSum(Owner owner, EquityAccount equityAccount);

	@Query("SELECT COALESCE(SUM(e.value), 0) FROM TransferEntry e WHERE e.outOwner = ?1 and e.outAccount = ?2")
	BigDecimal debitSum(Owner owner, EquityAccount equityAccount);

}
