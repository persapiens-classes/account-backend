package org.persapiens.account.controller;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.service.AccountService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
public abstract class AccountController<D extends AccountDTO, E extends Account>
		extends CrudController<D, D, D, String, E, Long> {

	private AccountService<D, E> accountService;

	@GetMapping("/{description}")
	public Optional<D> findByDescription(@PathVariable String description) {
		return this.accountService.findByDescription(description);
	}

	@DeleteMapping("/{description}")
	public void deleteByDescription(@PathVariable(required = true) String description) {
		this.accountService.deleteByDescription(description);
	}

	@PutMapping("/{description}")
	public D update(@PathVariable String description, @RequestBody D dto) {
		D result = null;
		Optional<D> accountOptional = this.accountService.update(description, dto);
		if (accountOptional.isPresent()) {
			result = accountOptional.get();
		}
		return result;
	}

}
