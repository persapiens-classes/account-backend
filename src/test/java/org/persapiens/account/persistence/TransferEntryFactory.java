package org.persapiens.account.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.TransferEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferEntryFactory {

	@Autowired
	private TransferEntryRepository entryRepository;

	public TransferEntry entry(Owner inOwner, Owner outOwner, EquityAccount inAccount, EquityAccount outAccount,
			BigDecimal value) {
		TransferEntry entry = TransferEntry.builder()
			.inAccount(inAccount)
			.outAccount(outAccount)
			.date(LocalDateTime.now())
			.inOwner(inOwner)
			.outOwner(outOwner)
			.value(value.setScale(2))
			.build();
		this.entryRepository.save(entry);
		return entry;
	}

}
