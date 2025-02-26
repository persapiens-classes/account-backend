package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.domain.OwnerEquityAccountInitialValueId;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.dto.OwnerNameEquityAccountDescription;
import org.persapiens.account.persistence.EquityAccountRepository;
import org.persapiens.account.persistence.OwnerEquityAccountInitialValueRepository;
import org.persapiens.account.persistence.OwnerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class OwnerEquityAccountInitialValueService extends
		CrudService<OwnerEquityAccountInitialValueDTO, BigDecimal, OwnerEquityAccountInitialValueDTO, OwnerNameEquityAccountDescription, OwnerEquityAccountInitialValue, OwnerEquityAccountInitialValueId> {

	private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	private OwnerRepository ownerRepository;

	private EquityAccountRepository equityAccountRepository;

	private Owner validateOwner(String ownerName) {
		if (StringUtils.isBlank(ownerName)) {
			throw new IllegalArgumentException("Owner empty!");
		}
		Optional<Owner> byName = this.ownerRepository.findByName(ownerName);
		if (!byName.isPresent()) {
			throw new BeanExistsException("Owner not exists: " + ownerName);
		}
		return byName.get();
	}

	private EquityAccount validateEquityAccount(String equityAccountDescription) {
		if (StringUtils.isBlank(equityAccountDescription)) {
			throw new IllegalArgumentException("Equity Account empty!");
		}
		Optional<EquityAccount> byDescription = this.equityAccountRepository
			.findByDescription(equityAccountDescription);
		if (!byDescription.isPresent()) {
			throw new BeanExistsException("Equity Account not exists: " + equityAccountDescription);
		}
		return byDescription.get();
	}

	@Override
	protected OwnerEquityAccountInitialValueDTO toDTO(OwnerEquityAccountInitialValue entity) {
		return new OwnerEquityAccountInitialValueDTO(entity.getOwner().getName(),
				entity.getEquityAccount().getDescription(), entity.getValue());
	}

	@Override
	protected OwnerEquityAccountInitialValue insertDtoToEntity(
			OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValueDTO) {
		Owner owner = this.ownerRepository.findByName(ownerEquityAccountInitialValueDTO.owner()).get();
		EquityAccount equityAccount = this.equityAccountRepository
			.findByDescription(ownerEquityAccountInitialValueDTO.equityAccount())
			.get();
		return OwnerEquityAccountInitialValue.builder()
			.value(ownerEquityAccountInitialValueDTO.value())
			.owner(owner)
			.equityAccount(equityAccount)
			.build();
	}

	@Override
	protected OwnerEquityAccountInitialValue updateDtoToEntity(BigDecimal value) {
		return OwnerEquityAccountInitialValue.builder().value(value).build();
	}

	@Override
	protected Optional<OwnerEquityAccountInitialValue> findByUpdateKey(OwnerNameEquityAccountDescription updateKey) {
		Owner owner = validateOwner(updateKey.ownerName());
		EquityAccount equityAccount = validateEquityAccount(updateKey.equityAccountDescription());

		Optional<OwnerEquityAccountInitialValue> byOwnerAndEquityAccount = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(owner, equityAccount);
		if (byOwnerAndEquityAccount.isEmpty()) {
			throw new BeanNotFoundException("OwnerEquityAccountInitialValue not exists: " + owner.getName() + "-"
					+ equityAccount.getDescription());
		}

		return this.ownerEquityAccountInitialValueRepository.findByOwnerAndEquityAccount(owner, equityAccount);
	}

	@Override
	protected OwnerEquityAccountInitialValue setIdToUpdate(OwnerEquityAccountInitialValue t,
			OwnerEquityAccountInitialValue updateEntity) {
		updateEntity.setOwner(t.getOwner());
		updateEntity.setEquityAccount(t.getEquityAccount());
		updateEntity.setId(t.getId());
		return updateEntity;
	}

	@Override
	protected void validateInsert(OwnerEquityAccountInitialValueDTO insertDto) {
		Owner owner = validateOwner(insertDto.owner());
		EquityAccount equityAccount = validateEquityAccount(insertDto.equityAccount());

		Optional<OwnerEquityAccountInitialValue> byOwnerAndEquityAccount = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(owner, equityAccount);
		if (byOwnerAndEquityAccount.isPresent()) {
			throw new BeanExistsException(
					"OwnerEquityAccountInitialValue exists: " + owner.getName() + "-" + equityAccount.getDescription());
		}
	}

	public OwnerEquityAccountInitialValueDTO findByOwnerAndEquityAccount(String ownerName,
			String equityAccountDescription) {
		Optional<OwnerEquityAccountInitialValue> byOwnerAndEquityAccount = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(validateOwner(ownerName), validateEquityAccount(equityAccountDescription));
		if (byOwnerAndEquityAccount.isPresent()) {
			return toDTO(byOwnerAndEquityAccount.get());
		}
		else {
			throw new BeanNotFoundException("Bean not found by: " + ownerName + "-" + equityAccountDescription);
		}
	}

	@Transactional
	public void deleteByOwnderAndEquityAccount(String ownerName, String equityAccountDescription) {
		if (this.ownerEquityAccountInitialValueRepository.deleteByOwnerAndEquityAccount(validateOwner(ownerName),
				validateEquityAccount(equityAccountDescription)) == 0) {
			throw new BeanNotFoundException("Bean not found by: " + ownerName + "-" + equityAccountDescription);
		}
	}

}
