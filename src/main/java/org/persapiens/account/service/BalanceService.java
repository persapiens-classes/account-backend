package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BalanceService {

	private final EntryService entryService;

	private OwnerService ownerService;

	private EquityAccountService equityAccountService;

	private final OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService;

	private void validateBlank(String ownerName, String equityAccountDescription) {
		if (StringUtils.isBlank(ownerName)) {
			throw new IllegalArgumentException("Owner empty!");
		}
		if (StringUtils.isBlank(equityAccountDescription)) {
			throw new IllegalArgumentException("Equity Account empty!");
		}
	}

	private void validateFields(String ownerName, String equityAccountDescription, Optional<OwnerDTO> ownerOptional,
			Optional<EquityAccountDTO> equityAccountOptional) {
		if (ownerOptional.isEmpty()) {
			throw new BeanNotFoundException("Owner not exists: " + ownerName);
		}
		if (equityAccountOptional.isEmpty()) {
			throw new BeanNotFoundException("Equity Account not exists: " + equityAccountDescription);
		}
	}

	public BigDecimal balance(String ownerName, String equityAccountDescription) {
		validateBlank(ownerName, equityAccountDescription);

		Optional<OwnerDTO> ownerOptional = this.ownerService.findByName(ownerName);
		Optional<EquityAccountDTO> equityAccountOptional = this.equityAccountService
			.findByDescription(equityAccountDescription);

		validateFields(ownerName, equityAccountDescription, ownerOptional, equityAccountOptional);

		OwnerDTO owner = ownerOptional.get();
		EquityAccountDTO equityAccount = equityAccountOptional.get();

		Optional<OwnerEquityAccountInitialValueDTO> ownerAndEquityAccountInitialValueOptional = this.ownerEquityAccountInitialValueService
			.findByOwnerAndEquityAccount(owner, equityAccount);
		if (ownerAndEquityAccountInitialValueOptional.isEmpty()) {
			throw new BeanNotFoundException(
					"OwnerEquityAccountInitialValue not exists: " + ownerName + '-' + equityAccountDescription);
		}

		// get initial value of owner and equity account
		BigDecimal result = ownerAndEquityAccountInitialValueOptional.get().getValue();

		// sum credits of owner and equity account
		BigDecimal credits = this.entryService.creditSum(owner, equityAccount);

		// subtract debits of owner and equity account
		BigDecimal debits = this.entryService.debitSum(owner, equityAccount);

		return result.add(credits).subtract(debits);
	}

}
