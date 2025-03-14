package org.persapiens.account.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.service.AccountService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
public abstract class AccountController<E extends Account<C>, C extends Category>
		extends CrudController<AccountDTO, AccountDTO, AccountDTO, String, E, Long> {

	private AccountService<E, C> accountService;

	@GetMapping("/{description}")
	public AccountDTO findByDescription(@PathVariable(required = true) String description) {
		return this.accountService.findByDescription(description);
	}

	@DeleteMapping("/{description}")
	public void deleteByDescription(@PathVariable(required = true) String description) {
		this.accountService.deleteByDescription(description);
	}

	@PutMapping("/{description}")
	public AccountDTO update(@PathVariable(required = true) String description,
			@Valid @RequestBody(required = true) AccountDTO accountDTO) {
		return this.accountService.update(description, accountDTO);
	}

}
