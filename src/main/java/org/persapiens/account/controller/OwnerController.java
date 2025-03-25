package org.persapiens.account.controller;

import jakarta.validation.Valid;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.service.OwnerService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners")
public class OwnerController extends CrudController<OwnerDTO, OwnerDTO, OwnerDTO, String, Owner, Long> {

	private OwnerService ownerService;

	public OwnerController(OwnerService ownerService) {
		super(ownerService);

		this.ownerService = ownerService;
	}

	@FindOneBeanDoc
	@GetMapping("/{name}")
	public OwnerDTO findByName(@PathVariable(required = true) String name) {
		return this.ownerService.findByName(name);
	}

	@DeleteBeanDoc
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{name}")
	public void deleteByName(@PathVariable(required = true) String name) {
		this.ownerService.deleteByName(name);
	}

	@UpdateBeanDoc
	@PutMapping("/{name}")
	public OwnerDTO update(@PathVariable(required = true) String name,
			@Valid @RequestBody(required = true) OwnerDTO ownerDTO) {
		return this.ownerService.update(name, ownerDTO);
	}

}
