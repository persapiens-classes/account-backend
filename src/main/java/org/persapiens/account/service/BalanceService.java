package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.persistence.CreditEntryRepository;
import org.persapiens.account.persistence.DebitEntryRepository;
import org.persapiens.account.persistence.OwnerEquityAccountInitialValueRepository;
import org.persapiens.account.persistence.TransferEntryRepository;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BalanceService {

	private DebitEntryRepository debitEntryRepository;

	private CreditEntryRepository creditEntryRepository;

	private TransferEntryRepository transferEntryRepository;

	private OwnerService ownerService;

	private EquityAccountService equityAccountService;

	private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	public BigDecimal balance(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		Optional<OwnerEquityAccountInitialValue> ownerAndEquityAccountInitialValueOptional = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(owner, equityAccount);
		if (ownerAndEquityAccountInitialValueOptional.isEmpty()) {
			throw new BeanNotFoundException(
					"OwnerEquityAccountInitialValue not exists: " + ownerName + '-' + equityAccountDescription);
		}

		// get initial value of owner and equity account
		BigDecimal result = ownerAndEquityAccountInitialValueOptional.get().getValue();

		// sum credits of owner and equity account
		BigDecimal credits = this.creditEntryRepository.creditSum(owner, equityAccount).getValue();

		// subtract debits of owner and equity account
		BigDecimal debits = this.debitEntryRepository.debitSum(owner, equityAccount).getValue();

		// sum transfer credits of owner and equity account
		BigDecimal transferCredits = this.transferEntryRepository.creditSum(owner, equityAccount).getValue();

		// subtract transfer debits of owner and equity account
		BigDecimal transferDebits = this.transferEntryRepository.debitSum(owner, equityAccount).getValue();

		return result.add(credits).subtract(debits).add(transferCredits).subtract(transferDebits);
	}

}
