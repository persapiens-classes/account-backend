package org.persapiens.account.controller;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.service.EquityAccountService;
import org.persapiens.account.service.OwnerEquityAccountInitialValueService;
import org.persapiens.account.service.OwnerService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/ownerEquityAccountInitialValues")
public class OwnerEquityAccountInitialValueController extends
		CrudController<OwnerEquityAccountInitialValueDTO, OwnerEquityAccountInitialValueDTO, OwnerEquityAccountInitialValue, Long> {

	private OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService;

	private OwnerService ownerService;

	private EquityAccountService equityAccountService;

	@Override
	protected OwnerEquityAccountInitialValue toEntity(OwnerEquityAccountInitialValueDTO dto) {
		Owner owner = this.ownerService.findByName(dto.getOwner()).get();
		EquityAccount equityAccount = this.equityAccountService.findByDescription(dto.getEquityAccount()).get();
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

	@GetMapping("/filter")
	public Optional<OwnerEquityAccountInitialValueDTO> findByOwnerAndEquityAccount(
			@RequestParam(required = true) String owner, @RequestParam(required = true) String equityAccount) {
		return toDTOOptional(this.ownerEquityAccountInitialValueService.findByOwnerAndEquityAccount(
				this.ownerService.findByName(owner).get(),
				this.equityAccountService.findByDescription(equityAccount).get()));
	}

	@DeleteMapping
	public void deleteByOwnderAndEquityAccount(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		this.ownerEquityAccountInitialValueService.deleteByOwnderAndEquityAccount(
				this.ownerService.findByName(owner).get(),
				this.equityAccountService.findByDescription(equityAccount).get());
	}

	@PutMapping
	public OwnerEquityAccountInitialValueDTO update(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount, @RequestBody BigDecimal value) {
		OwnerEquityAccountInitialValueDTO result = null;
		Optional<OwnerEquityAccountInitialValue> ownerEquityAccountInitialValueOptional = this.ownerEquityAccountInitialValueService
			.findByOwnerAndEquityAccount(this.ownerService.findByName(owner).get(),
					this.equityAccountService.findByDescription(equityAccount).get());
		if (ownerEquityAccountInitialValueOptional.isPresent()) {
			OwnerEquityAccountInitialValue currentOwnerEquityAccountInitialValue = ownerEquityAccountInitialValueOptional
				.get();
			OwnerEquityAccountInitialValue entityToSave = currentOwnerEquityAccountInitialValue;
			entityToSave.setValue(value);
			OwnerEquityAccountInitialValue saved = this.ownerEquityAccountInitialValueService.save(entityToSave);
			result = toDTO(saved);
		}
		return result;
	}

}
