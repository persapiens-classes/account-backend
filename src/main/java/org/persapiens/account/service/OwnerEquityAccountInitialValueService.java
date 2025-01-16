package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.persistence.OwnerEquityAccountInitialValueRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class OwnerEquityAccountInitialValueService extends CrudService<OwnerEquityAccountInitialValue, Long> {

	private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	public Optional<OwnerEquityAccountInitialValue> findByOwnerAndEquityAccount(Owner owner,
			EquityAccount equityAccount) {
		return this.ownerEquityAccountInitialValueRepository.findByOwnerAndEquityAccount(owner, equityAccount);
	}

	@Transactional
	public void deleteByOwnderAndEquityAccount(Owner owner, EquityAccount equityAccount) {
		this.ownerEquityAccountInitialValueRepository.deleteByOwnerAndEquityAccount(owner, equityAccount);
	}

}
