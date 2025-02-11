package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TransferService {

	private final EntryService entryService;

	private final DebitAccountService debitAccountService;

	private final CreditAccountService creditAccountService;

	@Transactional
	public void transfer(BigDecimal value, OwnerDTO debitOwnerDTO, EquityAccountDTO debitEquityAccountDTO,
			OwnerDTO creditOwnerDTO, EquityAccountDTO creditEquityAccountDTO) {

		DebitAccountDTO expenseTransferDTO = this.debitAccountService.expenseTransfer();
		CreditAccountDTO incomeTransferDTO = this.creditAccountService.incomeTransfer();

		if (creditOwnerDTO.equals(debitOwnerDTO)) {
			throw new IllegalArgumentException("Owners should be different: " + debitOwnerDTO + " = " + creditOwnerDTO);
		}

		LocalDateTime date = LocalDateTime.now();

		EntryInsertUpdateDTO debitEntry = EntryInsertUpdateDTO.builder()
			.inAccount(expenseTransferDTO.getDescription())
			.outAccount(debitEquityAccountDTO.getDescription())
			.owner(debitOwnerDTO.getName())
			.value(value)
			.date(date)
			.note("Transfer debit entry")
			.build();
		this.entryService.insert(debitEntry);

		EntryInsertUpdateDTO creditEntry = EntryInsertUpdateDTO.builder()
			.inAccount(creditEquityAccountDTO.getDescription())
			.outAccount(incomeTransferDTO.getDescription())
			.owner(creditOwnerDTO.getName())
			.value(value)
			.date(date)
			.note("Transfer credit entry")
			.build();
		this.entryService.insert(creditEntry);
	}

}
