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
		if (StringUtils.isBlank(transferDTO.debitOwner())) {
			throw new IllegalArgumentException("Debit Owner empty!");
		}
		if (StringUtils.isBlank(transferDTO.debitAccount())) {
			throw new IllegalArgumentException("Debit Account empty!");
		}
		if (StringUtils.isBlank(transferDTO.creditOwner())) {
			throw new IllegalArgumentException("Credit Owner empty!");
		}
		if (StringUtils.isBlank(transferDTO.creditAccount())) {
			throw new IllegalArgumentException("Credit Account empty!");
		}
		if (transferDTO.value() == null) {
			throw new IllegalArgumentException("Value empty!");
		}
	}

	private void validateFields(TransferDTO transferDTO, Optional<Owner> debitOwnerOptional,
			Optional<EquityAccount> debitEquityAccountOptional, Optional<Owner> creditOwnerOptional,
			Optional<EquityAccount> creditEquityAccountOptional) {
		if (debitOwnerOptional.isEmpty()) {
			throw new AttributeNotFoundException("Debit Owner not exists: " + transferDTO.debitOwner());
		}
		if (debitEquityAccountOptional.isEmpty()) {
			throw new AttributeNotFoundException("Debit Equity Account not exists: " + transferDTO.debitAccount());
		}
		if (creditOwnerOptional.isEmpty()) {
			throw new AttributeNotFoundException("Credit Owner not exists: " + transferDTO.creditOwner());
		}
		if (creditEquityAccountOptional.isEmpty()) {
			throw new AttributeNotFoundException("Credit Equity Account not exists: " + transferDTO.creditAccount());
		}
	}

	@Transactional
	public void transfer(TransferDTO transferDTO) {
		validateBlank(transferDTO);

		Optional<Owner> debitOwnerOptional = this.ownerRepository.findByName(transferDTO.debitOwner());
		Optional<EquityAccount> debitEquityAccountOptional = this.equityAccountRepository
			.findByDescription(transferDTO.debitAccount());
		Optional<Owner> creditOwnerOptional = this.ownerRepository.findByName(transferDTO.creditOwner());
		Optional<EquityAccount> creditEquityAccountOptional = this.equityAccountRepository
			.findByDescription(transferDTO.creditAccount());

		validateFields(transferDTO, debitOwnerOptional, debitEquityAccountOptional, creditOwnerOptional,
				creditEquityAccountOptional);

		if (transferDTO.creditOwner().equals(transferDTO.debitOwner())) {
			throw new IllegalArgumentException("Owners should be different: " + transferDTO.creditOwner());
		}

		BigDecimal value = transferDTO.value();
		Owner debitOwnerDTO = debitOwnerOptional.get();
		EquityAccount debitEquityAccountDTO = debitEquityAccountOptional.get();
		Owner creditOwnerDTO = creditOwnerOptional.get();
		EquityAccount creditEquityAccountDTO = creditEquityAccountOptional.get();

		DebitAccountDTO expenseTransferDTO = this.debitAccountService.expenseTransfer();
		CreditAccountDTO incomeTransferDTO = this.creditAccountService.incomeTransfer();

		LocalDateTime date = LocalDateTime.now();

		EntryInsertUpdateDTO debitEntry = new EntryInsertUpdateDTO(debitOwnerDTO.getName(), date,
				expenseTransferDTO.description(), debitEquityAccountDTO.getDescription(), value,
				"Transfer debit entry");
		this.entryService.insert(debitEntry);

		EntryInsertUpdateDTO creditEntry = new EntryInsertUpdateDTO(creditOwnerDTO.getName(), date,
				creditEquityAccountDTO.getDescription(), incomeTransferDTO.description(), value,
				"Transfer credit entry");
		this.entryService.insert(creditEntry);
	}

}
