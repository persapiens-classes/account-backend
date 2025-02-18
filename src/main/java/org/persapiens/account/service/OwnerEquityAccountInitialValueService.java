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

	@Override
	protected OwnerEquityAccountInitialValueDTO toDTO(OwnerEquityAccountInitialValue entity) {
		return new OwnerEquityAccountInitialValueDTO(entity.getOwner().getName(),
				entity.getEquityAccount().getDescription(), entity.getValue());
	}

	@Override
	protected OwnerEquityAccountInitialValue insertDtoToEntity(OwnerEquityAccountInitialValueDTO dto) {
		Owner owner = this.ownerRepository.findByName(dto.owner()).get();
		EquityAccount equityAccount = this.equityAccountRepository.findByDescription(dto.equityAccount()).get();
		return OwnerEquityAccountInitialValue.builder()
			.value(dto.value())
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
		Owner owner = this.ownerRepository.findByName(updateKey.ownerName()).get();
		EquityAccount equityAccount = this.equityAccountRepository
			.findByDescription(updateKey.equityAccountDescription())
			.get();
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

	public OwnerEquityAccountInitialValueDTO findByOwnerAndEquityAccount(String ownerName,
			String equityAccountDescription) {
		validateBlank(ownerName, equityAccountDescription);

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
		validateBlank(ownerName, equityAccountDescription);

		if (this.ownerEquityAccountInitialValueRepository.deleteByOwnerAndEquityAccount(validateOwner(ownerName),
				validateEquityAccount(equityAccountDescription)) == 0) {
			throw new BeanNotFoundException("Bean not found by: " + ownerName + "-" + equityAccountDescription);
		}
	}

	private void validateBlank(String ownerName, String equityAccountDescription) {
		if (StringUtils.isBlank(ownerName)) {
			throw new IllegalArgumentException("Owner empty!");
		}
		if (StringUtils.isBlank(equityAccountDescription)) {
			throw new IllegalArgumentException("Equity Account empty!");
		}
	}

	private void validateBlank(String ownerName, String equityAccountDescription, BigDecimal value) {
		validateBlank(ownerName, equityAccountDescription);
		if (value == null) {
			throw new IllegalArgumentException("Value null!");
		}
	}

	private Owner validateOwner(String ownerName) {
		Optional<Owner> byName = this.ownerRepository.findByName(ownerName);
		if (!byName.isPresent()) {
			throw new BeanExistsException("Owner not exists: " + ownerName);
		}
		return byName.get();
	}

	private EquityAccount validateEquityAccount(String equityAccountDescription) {
		Optional<EquityAccount> byDescription = this.equityAccountRepository
			.findByDescription(equityAccountDescription);
		if (!byDescription.isPresent()) {
			throw new BeanExistsException("Equity Account not exists: " + equityAccountDescription);
		}
		return byDescription.get();
	}

	private void validate(String ownerName, String equityAccountDescription, BigDecimal value, boolean insert) {
		validateBlank(ownerName, equityAccountDescription, value);

		Optional<OwnerEquityAccountInitialValue> byOwnerAndEquityAccount = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(validateOwner(ownerName), validateEquityAccount(equityAccountDescription));
		if (insert && byOwnerAndEquityAccount.isPresent()) {
			throw new BeanExistsException(
					"OwnerEquityAccountInitialValue exists: " + ownerName + "-" + equityAccountDescription);
		}
		else if (!insert && byOwnerAndEquityAccount.isEmpty()) {
			throw new BeanNotFoundException(
					"OwnerEquityAccountInitialValue not exists: " + ownerName + "-" + equityAccountDescription);
		}
	}

	@Override
	public OwnerEquityAccountInitialValueDTO insert(OwnerEquityAccountInitialValueDTO insertDto) {
		validate(insertDto.owner(), insertDto.equityAccount(), insertDto.value(), true);
		return super.insert(insertDto);
	}

	@Override
	public OwnerEquityAccountInitialValueDTO update(OwnerNameEquityAccountDescription updateKey, BigDecimal updateDto) {
		validate(updateKey.ownerName(), updateKey.equityAccountDescription(), updateDto, false);
		return super.update(updateKey, updateDto);
	}

}
