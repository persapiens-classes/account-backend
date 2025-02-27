package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.persistence.EntryRepository;
import org.persapiens.account.persistence.OwnerEquityAccountInitialValueRepository;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BalanceService {

	private EntryRepository entryRepository;

	private OwnerService ownerService;

	private EquityAccountService equityAccountService;

	private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	public BigDecimal balance(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

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
