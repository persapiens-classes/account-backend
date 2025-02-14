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
	protected Owner toEntity(OwnerDTO dto) {
		return Owner.builder().name(dto.getName()).build();
	}

	@Override
	protected OwnerDTO toDTO(Owner entity) {
		return OwnerDTO.builder().name(entity.getName()).build();
	}

	@Override
	protected Owner insertDtoToEntity(OwnerDTO dto) {
		return toEntity(dto);
	}

	@Override
	protected Owner updateDtoToEntity(OwnerDTO dto) {
		return toEntity(dto);
	}

	@Override
	protected Optional<Owner> findByUpdateKey(String updateKey) {
		return this.ownerRepository.findByName(updateKey);
	}

	@Override
	protected Owner setIdToUpdate(Owner t, Owner updateEntity) {
		updateEntity.setId(t.getId());
		return updateEntity;
	}

	public Optional<OwnerDTO> findByName(String name) {
		return toOptionalDTO(this.ownerRepository.findByName(name));
	}

	@Transactional
	public void deleteByName(String name) {
		if (this.ownerRepository.deleteByName(name) == 0) {
			throw new BeanNotFoundException("Bean not found by: " + name);
		}
	}

	public void validate(OwnerDTO ownerDto) {
		if (StringUtils.isBlank(ownerDto.getName())) {
			throw new IllegalArgumentException("Name empty!");
		}
		if (findByName(ownerDto.getName()).isPresent()) {
			throw new BeanExistsException("Name exists: " + ownerDto.getName());
		}
	}

	@Override
	public OwnerDTO insert(OwnerDTO insertDto) {
		validate(insertDto);

		return super.insert(insertDto);
	}

	@Override
	public Optional<OwnerDTO> update(String updateKey, OwnerDTO updateDto) {
		validate(updateDto);

		return super.update(updateKey, updateDto);
	}

}
