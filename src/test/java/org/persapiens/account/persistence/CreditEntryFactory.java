package org.persapiens.account.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryFactory {

	@Autowired
	private CreditEntryRepository entryRepository;

	public CreditEntry entry(Owner owner, EquityAccount inAccount, CreditAccount outAccount, BigDecimal value) {
		CreditEntry entry = CreditEntry.builder()
			.inAccount(inAccount)
			.outAccount(outAccount)
			.date(LocalDateTime.now())
			.inOwner(owner)
			.outOwner(owner)
			.value(value.setScale(2))
			.build();
		this.entryRepository.save(entry);
		return entry;
	}

}
