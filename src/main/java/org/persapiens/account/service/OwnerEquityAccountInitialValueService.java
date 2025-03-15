package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.domain.OwnerEquityAccountInitialValueId;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.dto.OwnerNameEquityAccountDescription;
import org.persapiens.account.persistence.OwnerEquityAccountInitialValueRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerEquityAccountInitialValueService extends
		CrudService<OwnerEquityAccountInitialValueDTO, BigDecimal, OwnerEquityAccountInitialValueDTO, OwnerNameEquityAccountDescription, OwnerEquityAccountInitialValue, OwnerEquityAccountInitialValueId> {

	private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	private OwnerService ownerService;

	private EquityAccountService equityAccountService;

	public OwnerEquityAccountInitialValueService(
			OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository,
			OwnerService ownerService, EquityAccountService equityAccountService) {
		super(ownerEquityAccountInitialValueRepository);

		this.ownerEquityAccountInitialValueRepository = ownerEquityAccountInitialValueRepository;
		this.ownerService = ownerService;
		this.equityAccountService = equityAccountService;
	}

	@Override
	protected OwnerEquityAccountInitialValueDTO toDTO(OwnerEquityAccountInitialValue entity) {
		return new OwnerEquityAccountInitialValueDTO(entity.getOwner().getName(),
				entity.getEquityAccount().getDescription(), entity.getValue());
	}

	@Override
	protected OwnerEquityAccountInitialValue insertDtoToEntity(
			OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValueDTO) {
		Owner owner = this.ownerService.findEntityByName(ownerEquityAccountInitialValueDTO.owner());
		EquityAccount equityAccount = this.equityAccountService
			.findEntityByDescription(ownerEquityAccountInitialValueDTO.equityAccount());

		Optional<OwnerEquityAccountInitialValue> byOwnerAndEquityAccount = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(owner, equityAccount);
		if (byOwnerAndEquityAccount.isPresent()) {
			throw new BeanExistsException(
					"OwnerEquityAccountInitialValue exists: " + owner.getName() + "-" + equityAccount.getDescription());
		}

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

	OwnerEquityAccountInitialValue findEntityByOwnerAndEquityAccount(String ownerName,
			String equityAccountDescription) {
		Optional<OwnerEquityAccountInitialValue> byOwnerAndEquityAccount = this.ownerEquityAccountInitialValueRepository
			.findByOwnerAndEquityAccount(this.ownerService.findEntityByName(ownerName),
					this.equityAccountService.findEntityByDescription(equityAccountDescription));
		if (byOwnerAndEquityAccount.isPresent()) {
			return byOwnerAndEquityAccount.get();
		}
		else {
			throw new BeanNotFoundException(
					"OwnerEquityAccountInitialValue not found by: " + ownerName + "-" + equityAccountDescription);
		}
	}

	@Override
	protected OwnerEquityAccountInitialValue findByUpdateKey(OwnerNameEquityAccountDescription updateKey) {
		return findEntityByOwnerAndEquityAccount(updateKey.ownerName(), updateKey.equityAccountDescription());
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
		return toDTO(findEntityByOwnerAndEquityAccount(ownerName, equityAccountDescription));

	}

	@Transactional
	public void deleteByOwnderAndEquityAccount(String ownerName, String equityAccountDescription) {
		if (this.ownerEquityAccountInitialValueRepository.deleteByOwnerAndEquityAccount(
				this.ownerService.findEntityByName(ownerName),
				this.equityAccountService.findEntityByDescription(equityAccountDescription)) == 0) {
			throw new BeanNotFoundException("Bean not found by: " + ownerName + "-" + equityAccountDescription);
		}
	}

}
