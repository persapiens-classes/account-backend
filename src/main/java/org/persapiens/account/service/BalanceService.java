package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.BalanceDTO;
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

	private AccountService<EquityAccount, EquityCategory> equityAccountService;

	private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	public BalanceDTO balanceByOwnerAndEquityAccount(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		Optional<OwnerEquityAccountInitialValue> ownerAndEquityAccountInitialValueOptional = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(owner, equityAccount);
		if (ownerAndEquityAccountInitialValueOptional.isEmpty()) {
			throw new BeanNotFoundException(
					"OwnerEquityAccountInitialValue not exists: " + ownerName + '-' + equityAccountDescription);
		}

		// get owner and equity account initial value
		return balanceByOwnerAndEquityAccountInitialValue(ownerAndEquityAccountInitialValueOptional.get());
	}

	public List<BalanceDTO> balanceAll() {
		List<BalanceDTO> result = new ArrayList<>();
		for (var bean : this.ownerEquityAccountInitialValueRepository.findAll()) {
			result.add(balanceByOwnerAndEquityAccountInitialValue(bean));
		}
		return result;
	}

	private BalanceDTO balanceByOwnerAndEquityAccountInitialValue(
			OwnerEquityAccountInitialValue ownerAndEquityAccountInitialValue) {
		Owner owner = ownerAndEquityAccountInitialValue.getOwner();
		EquityAccount equityAccount = ownerAndEquityAccountInitialValue.getEquityAccount();

		// get initial value of owner and equity account
		BigDecimal result = ownerAndEquityAccountInitialValue.getInitialValue();

		// sum credits of owner and equity account
		BigDecimal credits = this.creditEntryRepository.creditSum(owner, equityAccount);

		// subtract debits of owner and equity account
		BigDecimal debits = this.debitEntryRepository.debitSum(owner, equityAccount);

		// sum transfer credits of owner and equity account
		BigDecimal transferCredits = this.transferEntryRepository.creditSum(owner, equityAccount);

		// subtract transfer debits of owner and equity account
		BigDecimal transferDebits = this.transferEntryRepository.debitSum(owner, equityAccount);

		result = result.add(credits).subtract(debits).add(transferCredits).subtract(transferDebits);

		AccountDTO equityAccountDTO = new AccountDTO(equityAccount.getDescription(),
				equityAccount.getCategory().getDescription());

		return new BalanceDTO(owner.getName(), equityAccountDTO, ownerAndEquityAccountInitialValue.getInitialValue(),
				result);
	}

}
