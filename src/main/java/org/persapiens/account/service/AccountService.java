package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.persistence.AccountRepository;
import org.persapiens.account.persistence.CategoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public abstract class AccountService<D extends AccountDTO, E extends Account>
		extends CrudService<D, D, D, String, E, Long> {

	private AccountRepository<E> accountRepository;

	private CategoryRepository categoryRepository;

	protected abstract E createAccount();

	protected abstract D createAccountDTO();

	@Override
	public E toEntity(D dto) {
		E result = createAccount();
		result.setDescription(dto.getDescription());
		result.setCategory(this.categoryRepository.findByDescription(dto.getCategory()).get());
		return result;
	}

	@Override
	protected D toDTO(E entity) {
		D result = createAccountDTO();
		result.setDescription(entity.getDescription());
		result.setCategory(entity.getCategory().getDescription());
		return result;
	}

	@Override
	protected E insertDtoToEntity(D dto) {
		return toEntity(dto);
	}

	@Override
	protected E updateDtoToEntity(D dto) {
		return toEntity(dto);
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

	public Optional<D> findByDescription(String description) {
		return toOptionalDTO(this.accountRepository.findByDescription(description));
	}

	@Transactional
	public void deleteByDescription(String description) {
		this.accountRepository.deleteByDescription(description);
	}

}
