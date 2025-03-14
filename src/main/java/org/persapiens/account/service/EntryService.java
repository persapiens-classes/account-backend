package org.persapiens.account.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.persistence.EntryRepository;

import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public abstract class EntryService <T extends Entry<I, D, O, E>, I extends Account<D>, D extends Category, O extends Account<E>, E extends Category>
	extends CrudService<EntryInsertUpdateDTO, EntryInsertUpdateDTO, EntryDTO, Long, T, Long> {

	private EntryRepository<T, I, D, O, E> entryRepository;

	private AccountService<I, D> inAccountService;

	private AccountService<O, E> outAccountService;

	private OwnerService ownerService;

	protected abstract T createEntry();

	@Override
	protected EntryDTO toDTO(T entry) {
		return new EntryDTO(entry.getId(), entry.getInOwner().getName(), entry.getOutOwner().getName(), entry.getDate(),
				new AccountDTO(entry.getInAccount().getDescription(),
						entry.getInAccount().getCategory().getDescription()),
				new AccountDTO(entry.getOutAccount().getDescription(),
						entry.getOutAccount().getCategory().getDescription()),
				entry.getValue(), entry.getNote());
	}

	private T toEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		T result = createEntry();

		result.setInOwner(this.ownerService.findEntityByName(entryInsertUpdateDTO.inOwner()));
		result.setOutOwner(this.ownerService.findEntityByName(entryInsertUpdateDTO.outOwner()));
		result.setInAccount(this.inAccountService.findEntityByDescription(entryInsertUpdateDTO.inAccount()));
		result.setOutAccount(this.outAccountService.findEntityByDescription(entryInsertUpdateDTO.outAccount()));
		result.setDate(entryInsertUpdateDTO.date());
		result.setValue(entryInsertUpdateDTO.value());
		result.setNote(entryInsertUpdateDTO.note());

		return result;
	}

	@Override
	protected T insertDtoToEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		return toEntity(entryInsertUpdateDTO);
	}

	@Override
	protected T updateDtoToEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		return toEntity(entryInsertUpdateDTO);
	}

	T findEntityById(Long id) {
		Optional<T> byId = this.entryRepository.findById(id);
		if (byId.isPresent()) {
			return byId.get();
		}
		else {
			throw new BeanNotFoundException("Entry not found by: " + id);
		}
	}

	@Override
	protected T findByUpdateKey(Long updateKey) {
		return findEntityById(updateKey);
	}

	@Override
	protected T setIdToUpdate(T t, T updateEntity) {
		updateEntity.setId(t.getId());
		return updateEntity;
	}

	@Transactional
	public void deleteById(long id) {
		if (this.entryRepository.existsById(id)) {
			this.entryRepository.deleteById(id);
		}
		else {
			throw new BeanNotFoundException("Entry not found by: " + id);
		}
	}

	public EntryDTO findById(Long id) {
		return toDTO(findEntityById(id));
	}

}
