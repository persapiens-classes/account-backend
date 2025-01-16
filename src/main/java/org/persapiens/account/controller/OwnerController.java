package org.persapiens.account.controller;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.service.OwnerService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/owners")
public class OwnerController extends CrudController<OwnerDTO, OwnerDTO, Owner, Long> {

	private OwnerService ownerService;

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

	@GetMapping("/{name}")
	public Optional<OwnerDTO> findByName(@PathVariable String name) {
		return toDTOOptional(this.ownerService.findByName(name));
	}

	@DeleteMapping("/{name}")
	public void deleteByName(@PathVariable String name) {
		this.ownerService.deleteByName(name);
	}

	@PutMapping("/{name}")
	public OwnerDTO update(@PathVariable String name, @RequestBody OwnerDTO dto) {
		OwnerDTO result = null;
		Optional<Owner> ownerOptional = this.ownerService.findByName(name);
		if (ownerOptional.isPresent()) {
			Owner currentOwner = ownerOptional.get();
			Owner entityToSave = toEntity(dto);
			entityToSave.setId(currentOwner.getId());
			Owner saved = this.ownerService.save(entityToSave);
			result = toDTO(saved);
		}
		return result;
	}

}
