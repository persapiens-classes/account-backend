package org.persapiens.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.dto.TransferDTO;
import org.persapiens.account.persistence.EquityAccountRepository;
import org.persapiens.account.persistence.OwnerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TransferService {

	private final EntryService entryService;

	private final DebitAccountService debitAccountService;

	private final CreditAccountService creditAccountService;

	private final OwnerRepository ownerRepository;

	private final EquityAccountRepository equityAccountRepository;

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

	private void validateFields(TransferDTO transferDTO, Optional<Owner> debitOwnerOptional,
			Optional<EquityAccount> debitEquityAccountOptional, Optional<Owner> creditOwnerOptional,
			Optional<EquityAccount> creditEquityAccountOptional) {
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

		Optional<Owner> debitOwnerOptional = this.ownerRepository.findByName(transferDTO.getDebitOwner());
		Optional<EquityAccount> debitEquityAccountOptional = this.equityAccountRepository
			.findByDescription(transferDTO.getDebitAccount());
		Optional<Owner> creditOwnerOptional = this.ownerRepository.findByName(transferDTO.getCreditOwner());
		Optional<EquityAccount> creditEquityAccountOptional = this.equityAccountRepository
			.findByDescription(transferDTO.getCreditAccount());

		validateFields(transferDTO, debitOwnerOptional, debitEquityAccountOptional, creditOwnerOptional,
				creditEquityAccountOptional);

		if (transferDTO.getCreditOwner().equals(transferDTO.getDebitOwner())) {
			throw new IllegalArgumentException("Owners should be different: " + transferDTO.getCreditOwner());
		}

		BigDecimal value = transferDTO.getValue();
		Owner debitOwnerDTO = debitOwnerOptional.get();
		EquityAccount debitEquityAccountDTO = debitEquityAccountOptional.get();
		Owner creditOwnerDTO = creditOwnerOptional.get();
		EquityAccount creditEquityAccountDTO = creditEquityAccountOptional.get();

		DebitAccountDTO expenseTransferDTO = this.debitAccountService.expenseTransfer();
		CreditAccountDTO incomeTransferDTO = this.creditAccountService.incomeTransfer();

		LocalDateTime date = LocalDateTime.now();

		EntryInsertUpdateDTO debitEntry = EntryInsertUpdateDTO.builder()
			.inAccount(expenseTransferDTO.description())
			.outAccount(debitEquityAccountDTO.getDescription())
			.owner(debitOwnerDTO.getName())
			.value(value)
			.date(date)
			.note("Transfer debit entry")
			.build();
		this.entryService.insert(debitEntry);

		EntryInsertUpdateDTO creditEntry = EntryInsertUpdateDTO.builder()
			.inAccount(creditEquityAccountDTO.getDescription())
			.outAccount(incomeTransferDTO.description())
			.owner(creditOwnerDTO.getName())
			.value(value)
			.date(date)
			.note("Transfer credit entry")
			.build();
		this.entryService.insert(creditEntry);
	}

}
