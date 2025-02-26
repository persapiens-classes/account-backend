package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
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

	private Owner toEntity(OwnerDTO dto) {
		return Owner.builder().name(dto.name()).build();
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

	public OwnerDTO findByName(String name) {
		Optional<Owner> byName = this.ownerRepository.findByName(name);
		if (byName.isPresent()) {
			return toDTO(byName.get());
		}
		else {
			throw new BeanNotFoundException("Bean not found by: " + name);
		}
	}

	@Transactional
	public void deleteByName(String name) {
		if (this.ownerRepository.deleteByName(name) == 0) {
			throw new BeanNotFoundException("Bean not found by: " + name);
		}
	}

	private void validate(OwnerDTO ownerDto) {
		if (this.ownerRepository.findByName(ownerDto.name()).isPresent()) {
			throw new BeanExistsException("Name exists: " + ownerDto.name());
		}
	}

	@Override
	public OwnerDTO insert(OwnerDTO insertDto) {
		validate(insertDto);

		return super.insert(insertDto);
	}

	@Override
	public OwnerDTO update(String updateKey, OwnerDTO updateDto) {
		validate(updateDto);

		return super.update(updateKey, updateDto);
	}

}
