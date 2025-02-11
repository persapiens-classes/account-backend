package org.persapiens.account.controller;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.domain.OwnerEquityAccountInitialValueId;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.dto.OwnerNameEquityAccountDescription;
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
		CrudController<OwnerEquityAccountInitialValueDTO, BigDecimal, OwnerEquityAccountInitialValueDTO, OwnerNameEquityAccountDescription, OwnerEquityAccountInitialValue, OwnerEquityAccountInitialValueId> {

	private OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService;

	private OwnerService ownerService;

	private EquityAccountService equityAccountService;

	@GetMapping("/filter")
	public Optional<OwnerEquityAccountInitialValueDTO> findByOwnerAndEquityAccount(
			@RequestParam(required = true) String owner, @RequestParam(required = true) String equityAccount) {
		return this.ownerEquityAccountInitialValueService.findByOwnerAndEquityAccount(
				this.ownerService.findByName(owner).get(),
				this.equityAccountService.findByDescription(equityAccount).get());
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

		OwnerNameEquityAccountDescription updateKey = OwnerNameEquityAccountDescription.builder()
			.ownerName(owner)
			.equityAccountDescription(equityAccount)
			.build();
		Optional<OwnerEquityAccountInitialValueDTO> ownerEquityAccountInitialValueOptional = this.ownerEquityAccountInitialValueService
			.update(updateKey, value);
		if (ownerEquityAccountInitialValueOptional.isPresent()) {
			result = ownerEquityAccountInitialValueOptional.get();
		}
		return result;
	}

}
