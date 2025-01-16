package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TransferService {

	private final EntryService entryService;

	private final DebitAccountService debitAccountService;

	private final CreditAccountService creditAccountService;

	@Transactional
	public void transfer(BigDecimal value, Owner debitOwner, EquityAccount debitEquityAccount, Owner creditOwner,
			EquityAccount creditEquityAccount) {

		DebitAccount debitAccount = this.debitAccountService.expenseTransfer();
		CreditAccount creditAccount = this.creditAccountService.incomeTransfer();

		if (creditOwner.equals(debitOwner)) {
			throw new IllegalArgumentException("Owners should be different: " + debitOwner + " = " + creditOwner);
		}

		LocalDateTime date = LocalDateTime.now();

		Entry debitEntry = Entry.builder()
			.inAccount(debitAccount)
			.outAccount(debitEquityAccount)
			.owner(debitOwner)
			.value(value)
			.date(date)
			.note("Transfer debit entry")
			.build();
		this.entryService.save(debitEntry);

		Entry creditEntry = Entry.builder()
			.inAccount(creditEquityAccount)
			.outAccount(creditAccount)
			.owner(creditOwner)
			.value(value)
			.date(date)
			.note("Transfer credit entry")
			.build();
		this.entryService.save(creditEntry);
	}

}
