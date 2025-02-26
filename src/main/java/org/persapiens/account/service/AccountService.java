package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.dto.AccountDTOInterface;
import org.persapiens.account.persistence.AccountRepository;
import org.persapiens.account.persistence.CategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public abstract class AccountService<D extends AccountDTOInterface, E extends Account>
		extends CrudService<D, D, D, String, E, Long> {

	private AccountRepository<E> accountRepository;

	private CategoryRepository categoryRepository;

	protected abstract E createAccount();

	protected abstract D createAccountDTO(String description, String category);

	@Override
	protected D toDTO(E entity) {
		return createAccountDTO(entity.getDescription(), entity.getCategory().getDescription());
	}

	private E toEntity(D accountDTO) {
		E result = createAccount();
		result.setDescription(accountDTO.description());
		result.setCategory(this.categoryRepository.findByDescription(accountDTO.category()).get());
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

	@Override
	protected Optional<E> findByUpdateKey(String updateKey) {
		return this.accountRepository.findByDescription(updateKey);
	}

	@Override
	protected E setIdToUpdate(E t, E updateEntity) {
		updateEntity.setId(t.getId());
		return updateEntity;
	}

	private void validate(D accountDto) {
		if (this.accountRepository.findByDescription(accountDto.description()).isPresent()) {
			throw new BeanExistsException("Description exists: " + accountDto.description());
		}
		if (!this.categoryRepository.findByDescription(accountDto.category()).isPresent()) {
			throw new AttributeNotFoundException("Category not exists: " + accountDto.category());
		}
	}

	@Override
	protected void validateInsert(D insertDto) {
		validate(insertDto);
	}

	@Override
	protected void validateUpdate(D updateDto) {
		validate(updateDto);
	}

	public D findByDescription(String description) {
		Optional<E> accountOptional = this.accountRepository.findByDescription(description);
		if (accountOptional.isPresent()) {
			return toDTO(accountOptional.get());
		}
		else {
			throw new BeanNotFoundException("Bean not found by: " + description);
		}
	}

	@Transactional
	public void deleteByDescription(String description) {
		if (this.accountRepository.deleteByDescription(description) == 0) {
			throw new BeanNotFoundException("Bean not found by: " + description);
		}
	}

}
