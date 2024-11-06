package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;

import org.springframework.data.repository.CrudRepository;

public interface OwnerEquityAccountInitialValueRepository extends CrudRepository<OwnerEquityAccountInitialValue, Long> {

	Optional<OwnerEquityAccountInitialValue> findByOwnerAndEquityAccount(Owner owner, EquityAccount equityAccount);

}
