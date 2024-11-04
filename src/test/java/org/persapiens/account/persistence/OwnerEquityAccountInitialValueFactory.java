package org.persapiens.account.persistence;

import java.math.BigDecimal;
import java.util.Optional;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerEquityAccountInitialValueFactory {

    @Autowired
    private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	public OwnerEquityAccountInitialValue ownerEquityAccountInitialValue(
            Owner owner, EquityAccount equityAccount, BigDecimal value) {
        Optional<OwnerEquityAccountInitialValue> findBy = ownerEquityAccountInitialValueRepository.findByOwnerAndEquityAccount(owner, equityAccount);

        OwnerEquityAccountInitialValue ownerEquityAccountInitialValue;
        if (findBy.isEmpty()) {
            ownerEquityAccountInitialValue = OwnerEquityAccountInitialValue.builder()
                .owner(owner)
                .equityAccount(equityAccount)
                .build();
        } else {
            ownerEquityAccountInitialValue = findBy.get();
        }

        ownerEquityAccountInitialValue.setValue(value.setScale(2));

        return this.ownerEquityAccountInitialValueRepository.save(ownerEquityAccountInitialValue);
    }
}
