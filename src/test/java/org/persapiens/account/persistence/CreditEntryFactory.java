package org.persapiens.account.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreditEntryFactory {

	private CreditEntryRepository entryRepository;

	public CreditEntry entry(Owner owner, EquityAccount inAccount, CreditAccount outAccount, BigDecimal value) {
		CreditEntry entry = CreditEntry.builder()
			.inAccount(inAccount)
			.outAccount(outAccount)
			.inOwner(owner)
			.outOwner(owner)
			.date(LocalDateTime.now())
			.value(value.setScale(2))
			.build();
		this.entryRepository.save(entry);
		return entry;
	}

}
