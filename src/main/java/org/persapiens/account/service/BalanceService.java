package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.persistence.EntryRepository;
import org.persapiens.account.persistence.EquityAccountRepository;
import org.persapiens.account.persistence.OwnerEquityAccountInitialValueRepository;
import org.persapiens.account.persistence.OwnerRepository;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BalanceService {

	private EntryRepository entryRepository;

	private OwnerRepository ownerRepository;

	private EquityAccountRepository equityAccountRepository;

	private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	private void validateBlank(String ownerName, String equityAccountDescription) {
		if (StringUtils.isBlank(ownerName)) {
			throw new IllegalArgumentException("Owner empty!");
		}
		if (StringUtils.isBlank(equityAccountDescription)) {
			throw new IllegalArgumentException("Equity Account empty!");
		}
	}

	private void validateFields(String ownerName, String equityAccountDescription, Optional<Owner> ownerOptional,
			Optional<EquityAccount> equityAccountOptional) {
		if (ownerOptional.isEmpty()) {
			throw new AttributeNotFoundException("Owner not exists: " + ownerName);
		}
		if (equityAccountOptional.isEmpty()) {
			throw new AttributeNotFoundException("Equity Account not exists: " + equityAccountDescription);
		}
	}

	public BigDecimal balance(String ownerName, String equityAccountDescription) {
		validateBlank(ownerName, equityAccountDescription);

		Optional<Owner> ownerOptional = this.ownerRepository.findByName(ownerName);
		Optional<EquityAccount> equityAccountOptional = this.equityAccountRepository
			.findByDescription(equityAccountDescription);

		validateFields(ownerName, equityAccountDescription, ownerOptional, equityAccountOptional);

		Owner owner = ownerOptional.get();
		EquityAccount equityAccount = equityAccountOptional.get();

		Optional<OwnerEquityAccountInitialValue> ownerAndEquityAccountInitialValueOptional = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(owner, equityAccount);
		if (ownerAndEquityAccountInitialValueOptional.isEmpty()) {
			throw new AttributeNotFoundException(
					"OwnerEquityAccountInitialValue not exists: " + ownerName + '-' + equityAccountDescription);
		}

		// get initial value of owner and equity account
		BigDecimal result = ownerAndEquityAccountInitialValueOptional.get().getValue();

		// sum credits of owner and equity account
		BigDecimal credits = this.entryRepository.creditSum(owner, equityAccount).getValue();

		// subtract debits of owner and equity account
		BigDecimal debits = this.entryRepository.debitSum(owner, equityAccount).getValue();

		return result.add(credits).subtract(debits);
	}

}
