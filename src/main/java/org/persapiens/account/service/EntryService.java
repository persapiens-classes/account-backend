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
public abstract class EntryService <E extends Entry<E, I, J, O, P>, I extends Account<J>, J extends Category, O extends Account<P>, P extends Category> extends CrudService<EntryInsertUpdateDTO, EntryInsertUpdateDTO, EntryDTO, Long, E, Long> {

	private EntryRepository<E, I, J, O, P> entryRepository;

	private AccountService<I, J> inAccountService;

	private AccountService<O, P> outAccountService;

	private OwnerService ownerService;	

	@Override
	protected EntryDTO toDTO(E entry) {
		return new EntryDTO(entry.getId(), entry.getInOwner().getName(), entry.getOutOwner().getName(), 
				entry.getDate(),
				new AccountDTO(entry.getInAccount().getDescription(),
						entry.getInAccount().getCategory().getDescription()),
				new AccountDTO(entry.getOutAccount().getDescription(),
						entry.getOutAccount().getCategory().getDescription()),
				entry.getValue(), entry.getNote());
	}

	protected abstract E createEntry();

	private E toEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		E result = createEntry();
		result.setInAccount(this.inAccountService.findEntityByDescription(entryInsertUpdateDTO.inAccount()));
		result.setOutAccount(this.outAccountService.findEntityByDescription(entryInsertUpdateDTO.outAccount()));
		result.setInOwner(this.ownerService.findEntityByName(entryInsertUpdateDTO.inOwner()));
		result.setOutOwner(this.ownerService.findEntityByName(entryInsertUpdateDTO.outOwner()));
		result.setValue(entryInsertUpdateDTO.value());
		result.setDate(entryInsertUpdateDTO.date());
		result.setNote(entryInsertUpdateDTO.note());

		return result;
	}

	@Override
	protected E insertDtoToEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		return toEntity(entryInsertUpdateDTO);
	}

	@Override
	protected E updateDtoToEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		return toEntity(entryInsertUpdateDTO);
	}

	E findEntityById(Long id) {
		Optional<E> byId = this.entryRepository.findById(id);
		if (byId.isPresent()) {
			return byId.get();
		}
		else {
			throw new BeanNotFoundException("Entry not found by: " + id);
		}
	}

	@Override
	protected E findByUpdateKey(Long updateKey) {
		return findEntityById(updateKey);
	}

	@Override
	protected E setIdToUpdate(E t, E updateEntity) {
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
