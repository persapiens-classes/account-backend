package org.persapiens.account.controller;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.service.AccountService;
import org.persapiens.account.service.CategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
public abstract class AccountController<D extends AccountDTO, T extends Account> extends CrudController<D, D, T, Long> {

	private AccountService<T> accountService;

	private CategoryService categoryService;

	protected abstract T createAccount();

	protected abstract D createAccountDTO();

	@Override
	protected T toEntity(D dto) {
		T result = createAccount();
		result.setDescription(dto.getDescription());
		result.setCategory(this.categoryService.findByDescription(dto.getCategory()).get());
		return result;
	}

	@Override
	protected D toDTO(T entity) {
		D result = createAccountDTO();
		result.setDescription(entity.getDescription());
		result.setCategory(entity.getCategory().getDescription());
		return result;
	}

	@Override
	protected T insertDtoToEntity(D dto) {
		return toEntity(dto);
	}

	@GetMapping("/{description}")
	public Optional<D> findByDescription(@PathVariable String description) {
		return toDTOOptional(this.accountService.findByDescription(description));
	}

	@DeleteMapping("/{description}")
	public void deleteByDescription(@PathVariable String description) {
		this.accountService.deleteByDescription(description);
	}

	@PutMapping("/{description}")
	public D update(@PathVariable String description, @RequestBody D dto) {
		D result = null;
		Optional<T> accountOptional = this.accountService.findByDescription(description);
		if (accountOptional.isPresent()) {
			T currentAccount = accountOptional.get();
			T entityToSave = toEntity(dto);
			entityToSave.setId(currentAccount.getId());
			T saved = this.accountService.save(entityToSave);
			result = toDTO(saved);
		}
		return result;
	}

}
