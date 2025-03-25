package org.persapiens.account.controller;

import java.math.BigDecimal;

import org.persapiens.account.domain.OwnerEquityAccountInitialValue;
import org.persapiens.account.domain.OwnerEquityAccountInitialValueId;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueInsertDTO;
import org.persapiens.account.dto.OwnerNameEquityAccountDescription;
import org.persapiens.account.service.OwnerEquityAccountInitialValueService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ownerEquityAccountInitialValues")
public class OwnerEquityAccountInitialValueController extends
		CrudController<OwnerEquityAccountInitialValueInsertDTO, BigDecimal, OwnerEquityAccountInitialValueDTO, OwnerNameEquityAccountDescription, OwnerEquityAccountInitialValue, OwnerEquityAccountInitialValueId> {

	private OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService;

	public OwnerEquityAccountInitialValueController(
			OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService) {
		super(ownerEquityAccountInitialValueService);

		this.ownerEquityAccountInitialValueService = ownerEquityAccountInitialValueService;
	}

	@FindOneBeanDoc
	@GetMapping("/filter")
	public OwnerEquityAccountInitialValueDTO findByOwnerAndEquityAccount(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.ownerEquityAccountInitialValueService.findByOwnerAndEquityAccount(owner, equityAccount);
	}

	@DeleteBeanDoc
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping
	public void deleteByOwnderAndEquityAccount(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		this.ownerEquityAccountInitialValueService.deleteByOwnderAndEquityAccount(owner, equityAccount);
	}

	@UpdateBeanDoc
	@PutMapping
	public OwnerEquityAccountInitialValueDTO update(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount, @RequestBody(required = true) BigDecimal value) {
		OwnerNameEquityAccountDescription updateKey = new OwnerNameEquityAccountDescription(owner, equityAccount);
		return this.ownerEquityAccountInitialValueService.update(updateKey, value);
	}

}
