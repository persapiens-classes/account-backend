package org.persapiens.account.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.persistence.AccountRepository;

import org.springframework.transaction.annotation.Transactional;

public abstract class AccountService<E extends Account<C>, C extends Category>
		extends CrudService<AccountDTO, AccountDTO, AccountDTO, String, E, Long> {

	private AccountRepository<E, C> accountRepository;

	private CategoryService<C> categoryService;

	protected AccountService(AccountRepository<E, C> accountRepository, CategoryService<C> categoryService) {
		super(accountRepository);

		this.accountRepository = accountRepository;
		this.categoryService = categoryService;
	}

	protected abstract E createAccount();

	@Override
	protected AccountDTO toDTO(E entity) {
		return new AccountDTO(entity.getDescription(), entity.getCategory().getDescription());
	}

	private String validateAccountDescription(String description) {
		if (this.accountRepository.findByDescription(description).isPresent()) {
			throw new BeanExistsException("Description exists: " + description);
		}
		return description;
	}

	private E toEntity(AccountDTO accountDTO) {
		String description = validateAccountDescription(accountDTO.description());
		C category = this.categoryService.findEntityByDescription(accountDTO.category());

		E result = createAccount();
		result.setDescription(description);
		result.setCategory(category);
		return result;
	}

	@Override
	protected E insertDtoToEntity(AccountDTO accountDTO) {
		return toEntity(accountDTO);
	}

	@Override
	protected E updateDtoToEntity(AccountDTO accountDTO) {
		return toEntity(accountDTO);
	}

	E findEntityByDescription(String description) {
		if (StringUtils.isBlank(description)) {
			throw new IllegalArgumentException("Account description empty!");
		}
		Optional<E> accountOptional = this.accountRepository.findByDescription(description);
		if (accountOptional.isPresent()) {
			return accountOptional.get();
		}
		else {
			throw new BeanNotFoundException("Account not found by: " + description);
		}
	}

	@Override
	protected E findByUpdateKey(String updateKey) {
		return findEntityByDescription(updateKey);
	}

	@Override
	protected E setIdToUpdate(E t, E updateEntity) {
		updateEntity.setId(t.getId());
		return updateEntity;
	}

	public AccountDTO findByDescription(String description) {
		return toDTO(findEntityByDescription(description));
	}

	@Transactional
	public void deleteByDescription(String description) {
		if (this.accountRepository.deleteByDescription(description) == 0) {
			throw new BeanNotFoundException("Account not found by: " + description);
		}
	}

}
