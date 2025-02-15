package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
	protected D toDTO(E entity) {
		D result = createAccountDTO();
		result.setDescription(entity.getDescription());
		result.setCategory(entity.getCategory().getDescription());
		return result;
	}

	private E toEntity(D dto) {
		E result = createAccount();
		result.setDescription(dto.getDescription());
		result.setCategory(this.categoryRepository.findByDescription(dto.getCategory()).get());
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
		if (this.accountRepository.deleteByDescription(description) == 0) {
			throw new BeanNotFoundException("Bean not found by: " + description);
		}
	}

	private void validateBlank(D accountDto) {
		if (StringUtils.isBlank(accountDto.getDescription())) {
			throw new IllegalArgumentException("Description empty!");
		}
		if (StringUtils.isBlank(accountDto.getCategory())) {
			throw new IllegalArgumentException("Category empty!");
		}
	}

	public void validate(D accountDto) {
		validateBlank(accountDto);
		if (findByDescription(accountDto.getDescription()).isPresent()) {
			throw new BeanExistsException("Description exists: " + accountDto.getDescription());
		}
		if (!this.categoryRepository.findByDescription(accountDto.getCategory()).isPresent()) {
			throw new AttributeNotFoundException("Category not exists: " + accountDto.getCategory());
		}
	}

	@Override
	public D insert(D insertDto) {
		validate(insertDto);

		return super.insert(insertDto);
	}

	@Override
	public D update(String updateKey, D updateDto) {
		validate(updateDto);

		return super.update(updateKey, updateDto);
	}

}
