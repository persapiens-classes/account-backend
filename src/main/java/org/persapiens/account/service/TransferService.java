package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
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

	private void validateBlank(TransferDTO transferDTO) {
		if (StringUtils.isBlank(transferDTO.getDebitOwner())) {
			throw new IllegalArgumentException("Debit Owner empty!");
		}
		if (StringUtils.isBlank(transferDTO.getDebitAccount())) {
			throw new IllegalArgumentException("Debit Account empty!");
		}
		if (StringUtils.isBlank(transferDTO.getCreditOwner())) {
			throw new IllegalArgumentException("Credit Owner empty!");
		}
		if (StringUtils.isBlank(transferDTO.getCreditAccount())) {
			throw new IllegalArgumentException("Credit Account empty!");
		}
		if (transferDTO.getValue() == null) {
			throw new IllegalArgumentException("Value empty!");
		}
	}

	private void validateFields(TransferDTO transferDTO, Optional<OwnerDTO> debitOwnerOptional,
			Optional<EquityAccountDTO> debitEquityAccountOptional, Optional<OwnerDTO> creditOwnerOptional,
			Optional<EquityAccountDTO> creditEquityAccountOptional) {
		if (debitOwnerOptional.isEmpty()) {
			throw new AttributeNotFoundException("Debit Owner not exists: " + transferDTO.getDebitOwner());
		}
		if (debitEquityAccountOptional.isEmpty()) {
			throw new AttributeNotFoundException("Debit Equity Account not exists: " + transferDTO.getDebitAccount());
		}
		if (creditOwnerOptional.isEmpty()) {
			throw new AttributeNotFoundException("Credit Owner not exists: " + transferDTO.getCreditOwner());
		}
		if (creditEquityAccountOptional.isEmpty()) {
			throw new AttributeNotFoundException("Credit Equity Account not exists: " + transferDTO.getCreditAccount());
		}
	}

	@Transactional
	public void transfer(TransferDTO transferDTO) {
		validateBlank(transferDTO);

		Optional<OwnerDTO> debitOwnerOptional = this.ownerService.findByName(transferDTO.getDebitOwner());
		Optional<EquityAccountDTO> debitEquityAccountOptional = this.equityAccountService
			.findByDescription(transferDTO.getDebitAccount());
		Optional<OwnerDTO> creditOwnerOptional = this.ownerService.findByName(transferDTO.getCreditOwner());
		Optional<EquityAccountDTO> creditEquityAccountOptional = this.equityAccountService
			.findByDescription(transferDTO.getCreditAccount());

		validateFields(transferDTO, debitOwnerOptional, debitEquityAccountOptional, creditOwnerOptional,
				creditEquityAccountOptional);

		BigDecimal value = transferDTO.getValue();
		OwnerDTO debitOwnerDTO = debitOwnerOptional.get();
		EquityAccountDTO debitEquityAccountDTO = debitEquityAccountOptional.get();
		OwnerDTO creditOwnerDTO = creditOwnerOptional.get();
		EquityAccountDTO creditEquityAccountDTO = creditEquityAccountOptional.get();

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
