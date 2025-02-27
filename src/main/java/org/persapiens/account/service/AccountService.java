package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Category;
import org.persapiens.account.dto.AccountDTOInterface;
import org.persapiens.account.persistence.AccountRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public abstract class AccountService<D extends AccountDTOInterface, E extends Account>
		extends CrudService<D, D, D, String, E, Long> {

	private AccountRepository<E> accountRepository;

	private CategoryService categoryService;

	protected abstract E createAccount();

	protected abstract D createAccountDTO(String description, String category);

	@Override
	protected D toDTO(E entity) {
		return createAccountDTO(entity.getDescription(), entity.getCategory().getDescription());
	}

	private String validateAccountDescription(String description) {
		if (this.accountRepository.findByDescription(description).isPresent()) {
			throw new BeanExistsException("Description exists: " + description);
		}
		return description;
	}

	private E toEntity(D accountDTO) {
		String description = validateAccountDescription(accountDTO.description());
		Category category = this.categoryService.findEntityByDescription(accountDTO.category());

		E result = createAccount();
		result.setDescription(description);
		result.setCategory(category);
		return result;
	}

	@Override
	protected E insertDtoToEntity(D accountDTO) {
		return toEntity(accountDTO);
	}

	@Override
	protected E updateDtoToEntity(D accountDTO) {
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

	public D findByDescription(String description) {
		return toDTO(findEntityByDescription(description));
	}

	@Transactional
	public void deleteByDescription(String description) {
		if (this.accountRepository.deleteByDescription(description) == 0) {
			throw new BeanNotFoundException("Account not found by: " + description);
		}
	}

}
