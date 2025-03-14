package org.persapiens.account.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebitEntryFactory {

	@Autowired
	private DebitEntryRepository entryRepository;

	public DebitEntry entry(Owner owner, DebitAccount inAccount, EquityAccount outAccount, BigDecimal value) {
		DebitEntry entry = DebitEntry.builder()
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
