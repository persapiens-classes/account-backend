package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.dto.TransferDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TransferService {

	private final EntryService entryService;

	private final DebitAccountService debitAccountService;

	private final CreditAccountService creditAccountService;

	private final OwnerService ownerService;

	private final EquityAccountService equityAccountService;

	@Transactional
	public void transfer(TransferDTO transferDTO) {
		Owner debitOwner = this.ownerService.findEntityByName(transferDTO.debitOwner());
		EquityAccount debitEquityAccount = this.equityAccountService
			.findEntityByDescription(transferDTO.debitAccount());
		Owner creditOwner = this.ownerService.findEntityByName(transferDTO.creditOwner());
		EquityAccount creditEquityAccount = this.equityAccountService
			.findEntityByDescription(transferDTO.creditAccount());

		if (transferDTO.creditOwner().equals(transferDTO.debitOwner())) {
			throw new IllegalArgumentException("Owners should be different: " + transferDTO.creditOwner());
		}

		BigDecimal value = transferDTO.value();

		DebitAccountDTO expenseTransferDTO = this.debitAccountService.expenseTransfer();
		CreditAccountDTO incomeTransferDTO = this.creditAccountService.incomeTransfer();

		LocalDateTime date = LocalDateTime.now();

		EntryInsertUpdateDTO debitEntry = new EntryInsertUpdateDTO(debitOwner.getName(), date,
				expenseTransferDTO.description(), debitEquityAccount.getDescription(), value, "Transfer debit entry");
		this.entryService.insert(debitEntry);

		EntryInsertUpdateDTO creditEntry = new EntryInsertUpdateDTO(creditOwner.getName(), date,
				creditEquityAccount.getDescription(), incomeTransferDTO.description(), value, "Transfer credit entry");
		this.entryService.insert(creditEntry);
	}

}
