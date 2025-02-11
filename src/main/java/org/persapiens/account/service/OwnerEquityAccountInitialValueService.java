package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.domain.OwnerEquityAccountInitialValueId;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
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
	protected OwnerEquityAccountInitialValue toEntity(OwnerEquityAccountInitialValueDTO dto) {
		Owner owner = this.ownerRepository.findByName(dto.getOwner()).get();
		EquityAccount equityAccount = this.equityAccountRepository.findByDescription(dto.getEquityAccount()).get();
		return OwnerEquityAccountInitialValue.builder()
			.value(dto.getValue())
			.owner(owner)
			.equityAccount(equityAccount)
			.build();
	}

	@Override
	protected OwnerEquityAccountInitialValueDTO toDTO(OwnerEquityAccountInitialValue entity) {
		return OwnerEquityAccountInitialValueDTO.builder()
			.value(entity.getValue())
			.owner(entity.getOwner().getName())
			.equityAccount(entity.getEquityAccount().getDescription())
			.build();
	}

	@Override
	protected OwnerEquityAccountInitialValue insertDtoToEntity(OwnerEquityAccountInitialValueDTO dto) {
		return toEntity(dto);
	}

	@Override
	protected OwnerEquityAccountInitialValue updateDtoToEntity(BigDecimal value) {
		return OwnerEquityAccountInitialValue.builder().value(value).build();
	}

	@Override
	protected Optional<OwnerEquityAccountInitialValue> findByUpdateKey(OwnerNameEquityAccountDescription updateKey) {
		Owner owner = this.ownerRepository.findByName(updateKey.getOwnerName()).get();
		EquityAccount equityAccount = this.equityAccountRepository
			.findByDescription(updateKey.getEquityAccountDescription())
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

	public Optional<OwnerEquityAccountInitialValueDTO> findByOwnerAndEquityAccount(OwnerDTO ownerDTO,
			EquityAccountDTO equityAccountDTO) {
		Owner owner = this.ownerRepository.findByName(ownerDTO.getName()).get();
		EquityAccount equityAccount = this.equityAccountRepository.findByDescription(equityAccountDTO.getDescription())
			.get();
		return toOptionalDTO(
				this.ownerEquityAccountInitialValueRepository.findByOwnerAndEquityAccount(owner, equityAccount));
	}

	@Transactional
	public void deleteByOwnderAndEquityAccount(OwnerDTO ownerDTO, EquityAccountDTO equityAccountDTO) {
		Owner owner = this.ownerRepository.findByName(ownerDTO.getName()).get();
		EquityAccount equityAccount = this.equityAccountRepository.findByDescription(equityAccountDTO.getDescription())
			.get();
		this.ownerEquityAccountInitialValueRepository.deleteByOwnerAndEquityAccount(owner, equityAccount);
	}

}
