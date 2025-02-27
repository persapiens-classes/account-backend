package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.OwnerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class OwnerService extends CrudService<OwnerDTO, OwnerDTO, OwnerDTO, String, Owner, Long> {

	private OwnerRepository ownerRepository;

	@Override
	protected OwnerDTO toDTO(Owner entity) {
		return new OwnerDTO(entity.getName());
	}

	private void validate(OwnerDTO ownerDto) {
		if (this.ownerRepository.findByName(ownerDto.name()).isPresent()) {
			throw new BeanExistsException("Owner name exists: " + ownerDto.name());
		}
	}

	private Owner toEntity(OwnerDTO ownerDTO) {
		validate(ownerDTO);
		return Owner.builder().name(ownerDTO.name()).build();
	}

	@Override
	protected Owner insertDtoToEntity(OwnerDTO ownerDTO) {
		return toEntity(ownerDTO);
	}

	@Override
	protected Owner updateDtoToEntity(OwnerDTO ownerDTO) {
		return toEntity(ownerDTO);
	}

	Owner findEntityByName(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Owner name empty!");
		}
		Optional<Owner> byName = this.ownerRepository.findByName(name);
		if (byName.isPresent()) {
			return byName.get();
		}
		else {
			throw new BeanNotFoundException("Owner not found by: " + name);
		}
	}

	@Override
	protected Owner findByUpdateKey(String updateKey) {
		return findEntityByName(updateKey);
	}

	@Override
	protected Owner setIdToUpdate(Owner t, Owner updateEntity) {
		updateEntity.setId(t.getId());
		return updateEntity;
	}

	public OwnerDTO findByName(String name) {
		return toDTO(findEntityByName(name));
	}

	@Transactional
	public void deleteByName(String name) {
		if (this.ownerRepository.deleteByName(name) == 0) {
			throw new BeanNotFoundException("Owner not found by: " + name);
		}
	}

}
