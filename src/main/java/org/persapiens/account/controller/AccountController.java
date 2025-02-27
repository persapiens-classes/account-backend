package org.persapiens.account.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.dto.AccountDTOInterface;
import org.persapiens.account.service.AccountService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
public abstract class AccountController<D extends AccountDTOInterface, E extends Account>
		extends CrudController<D, D, D, String, E, Long> {

	private AccountService<D, E> accountService;

	@GetMapping("/{description}")
	public D findByDescription(@PathVariable(required = true) String description) {
		return this.accountService.findByDescription(description);
	}

	@DeleteMapping("/{description}")
	public void deleteByDescription(@PathVariable(required = true) String description) {
		this.accountService.deleteByDescription(description);
	}

	@PutMapping("/{description}")
	public D update(@PathVariable(required = true) String description,
			@Valid @RequestBody(required = true) D accountDTO) {
		return this.accountService.update(description, accountDTO);
	}

}
