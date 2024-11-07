package org.persapiens.account.controller;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.service.EquityAccountService;
import org.persapiens.account.service.OwnerEquityAccountInitialValueService;
import org.persapiens.account.service.OwnerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/ownerEquityAccountInitialValue")
public class OwnerEquityAccountInitialValueController
		extends CrudController<OwnerEquityAccountInitialValueDTO, OwnerEquityAccountInitialValue, Long> {

	private OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService;

	private OwnerService ownerService;

	private EquityAccountService equityAccountService;

	@Override
	protected OwnerEquityAccountInitialValue toEntity(OwnerEquityAccountInitialValueDTO dto) {
		return OwnerEquityAccountInitialValue.builder()
			.value(dto.getValue())
			.owner(this.ownerService.findByName(dto.getOwner().getName()).get())
			.equityAccount(this.equityAccountService.findByDescription(dto.getEquityAccount().getDescription()).get())
			.build();
	}

	@Override
	protected OwnerEquityAccountInitialValueDTO toDTO(OwnerEquityAccountInitialValue entity) {
		return OwnerEquityAccountInitialValueDTO.builder()
			.value(entity.getValue())
			.owner(OwnerDTO.builder().name(entity.getOwner().getName()).build())
			.equityAccount(EquityAccountDTO.builder().description(entity.getEquityAccount().getDescription()).build())
			.build();
	}

	@GetMapping("/findByOwnerAndEquityAccount")
	public Optional<OwnerEquityAccountInitialValueDTO> findByOwnerAndEquityAccount(@RequestParam String owner,
			@RequestParam String equityAccount) {
		return toDTOOptional(this.ownerEquityAccountInitialValueService.findByOwnerAndEquityAccount(
				this.ownerService.findByName(owner).get(),
				this.equityAccountService.findByDescription(equityAccount).get()));
	}

}
