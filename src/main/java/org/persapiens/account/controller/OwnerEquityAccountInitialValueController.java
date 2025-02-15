package org.persapiens.account.controller;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.domain.OwnerEquityAccountInitialValueId;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.dto.OwnerNameEquityAccountDescription;
import org.persapiens.account.service.OwnerEquityAccountInitialValueService;

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

	@GetMapping("/filter")
	public OwnerEquityAccountInitialValueDTO findByOwnerAndEquityAccount(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.ownerEquityAccountInitialValueService.findByOwnerAndEquityAccount(owner, equityAccount);
	}

	@DeleteMapping
	public void deleteByOwnderAndEquityAccount(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		this.ownerEquityAccountInitialValueService.deleteByOwnderAndEquityAccount(owner, equityAccount);
	}

	@PutMapping
	public OwnerEquityAccountInitialValueDTO update(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount, @RequestBody(required = true) BigDecimal value) {
		OwnerNameEquityAccountDescription updateKey = OwnerNameEquityAccountDescription.builder()
			.ownerName(owner)
			.equityAccountDescription(equityAccount)
			.build();
		return this.ownerEquityAccountInitialValueService.update(updateKey, value);
	}

}
