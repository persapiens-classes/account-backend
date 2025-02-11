package org.persapiens.account.service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BalanceService {

	private final EntryService entryService;

	private final OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService;

	public BigDecimal balance(OwnerDTO owner, EquityAccountDTO equityAccount) {
		// get initial value of owner and equity account
		BigDecimal result = this.ownerEquityAccountInitialValueService.findByOwnerAndEquityAccount(owner, equityAccount)
			.get()
			.getValue();

		// sum credits of owner and equity account
		BigDecimal credits = this.entryService.creditSum(owner, equityAccount);

		// subtract debits of owner and equity account
		BigDecimal debits = this.entryService.debitSum(owner, equityAccount);

		return result.add(credits).subtract(debits);
	}

}
