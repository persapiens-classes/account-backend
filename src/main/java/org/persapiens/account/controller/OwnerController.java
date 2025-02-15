package org.persapiens.account.controller;

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
public class OwnerController extends CrudController<OwnerDTO, OwnerDTO, OwnerDTO, String, Owner, Long> {

	private OwnerService ownerService;

	@GetMapping("/{name}")
	public OwnerDTO findByName(@PathVariable(required = true) String name) {
		return this.ownerService.findByName(name);
	}

	@DeleteMapping("/{name}")
	public void deleteByName(@PathVariable(required = true) String name) {
		this.ownerService.deleteByName(name);
	}

	@PutMapping("/{name}")
	public OwnerDTO update(@PathVariable(required = true) String name, @RequestBody(required = true) OwnerDTO dto) {
		return this.ownerService.update(name, dto);
	}

}
