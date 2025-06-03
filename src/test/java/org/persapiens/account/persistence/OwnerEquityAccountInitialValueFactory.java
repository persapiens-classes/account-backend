package org.persapiens.account.persistence;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OwnerEquityAccountInitialValueFactory {

	private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	public OwnerEquityAccountInitialValue ownerEquityAccountInitialValue(Owner owner, EquityAccount equityAccount,
			BigDecimal value) {
		Optional<OwnerEquityAccountInitialValue> findBy = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(owner, equityAccount);

		OwnerEquityAccountInitialValue ownerEquityAccountInitialValue;
		if (findBy.isEmpty()) {
			ownerEquityAccountInitialValue = OwnerEquityAccountInitialValue.builder()
				.owner(owner)
				.equityAccount(equityAccount)
				.build();
		}
		else {
			ownerEquityAccountInitialValue = findBy.get();
		}

		ownerEquityAccountInitialValue.setInitialValue(value.setScale(2));

		return this.ownerEquityAccountInitialValueRepository.save(ownerEquityAccountInitialValue);
	}

}
