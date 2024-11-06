package org.persapiens.account.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.domain.Owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntryFactory {

	@Autowired
	private EntryRepository entryRepository;

	public Entry entry(Owner owner, Account inAccount, Account outAccount, BigDecimal value) {
		Entry entry = Entry.builder()
			.inAccount(inAccount)
			.outAccount(outAccount)
			.date(LocalDateTime.now())
			.owner(owner)
			.value(value.setScale(2))
			.build();
		this.entryRepository.save(entry);
		return entry;
	}

}
