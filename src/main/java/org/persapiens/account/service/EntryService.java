package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.AccountRepository;
import org.persapiens.account.persistence.EntryRepository;
import org.persapiens.account.persistence.EquityAccountRepository;
import org.persapiens.account.persistence.OwnerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class EntryService extends CrudService<EntryInsertUpdateDTO, EntryInsertUpdateDTO, EntryDTO, Long, Entry, Long> {

	private EntryRepository entryRepository;

	private AccountRepository<Account> accountRepository;

	private OwnerRepository ownerRepository;

	private EquityAccountRepository equityAccountRepository;

	@Override
	protected EntryDTO toDTO(Entry entry) {
		return EntryDTO.builder()
			.id(entry.getId())
			.inAccount(AccountDTO.builder()
				.description(entry.getInAccount().getDescription())
				.category(entry.getInAccount().getCategory().getDescription())
				.build())
			.outAccount(AccountDTO.builder()
				.description(entry.getOutAccount().getDescription())
				.category(entry.getOutAccount().getCategory().getDescription())
				.build())
			.owner(entry.getOwner().getName())
			.date(entry.getDate())
			.value(entry.getValue())
			.note(entry.getNote())
			.build();
	}

	private Entry toEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		return Entry.builder()
			.inAccount(this.accountRepository.findByDescription(entryInsertUpdateDTO.getInAccount()).get())
			.outAccount(this.accountRepository.findByDescription(entryInsertUpdateDTO.getOutAccount()).get())
			.owner(this.ownerRepository.findByName(entryInsertUpdateDTO.getOwner()).get())
			.value(entryInsertUpdateDTO.getValue())
			.note(entryInsertUpdateDTO.getNote())
			.date(entryInsertUpdateDTO.getDate())
			.build();
	}

	@Override
	protected Entry insertDtoToEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		return toEntity(entryInsertUpdateDTO);
	}

	@Override
	protected Entry updateDtoToEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		return toEntity(entryInsertUpdateDTO);
	}

	@Override
	protected Optional<Entry> findByUpdateKey(Long updateKey) {
		return this.entryRepository.findById(updateKey);
	}

	@Override
	protected Entry setIdToUpdate(Entry t, Entry updateEntity) {
		updateEntity.setId(t.getId());
		return updateEntity;
	}

	private void validateBlank(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		if (StringUtils.isBlank(entryInsertUpdateDTO.getInAccount())) {
			throw new IllegalArgumentException("Description empty!");
		}
		if (StringUtils.isBlank(entryInsertUpdateDTO.getOutAccount())) {
			throw new IllegalArgumentException("Category empty!");
		}
		if (StringUtils.isBlank(entryInsertUpdateDTO.getOwner())) {
			throw new IllegalArgumentException("Owner empty!");
		}
		if (entryInsertUpdateDTO.getValue() == null) {
			throw new IllegalArgumentException("Value null!");
		}
		if (entryInsertUpdateDTO.getDate() == null) {
			throw new IllegalArgumentException("Date null!");
		}
	}

	private void validate(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		validateBlank(entryInsertUpdateDTO);
		if (!this.accountRepository.findByDescription(entryInsertUpdateDTO.getInAccount()).isPresent()) {
			throw new BeanExistsException("In account not exists: " + entryInsertUpdateDTO.getInAccount());
		}
		if (!this.accountRepository.findByDescription(entryInsertUpdateDTO.getOutAccount()).isPresent()) {
			throw new AttributeNotFoundException("Out account not exists: " + entryInsertUpdateDTO.getOutAccount());
		}
		if (!this.ownerRepository.findByName(entryInsertUpdateDTO.getOwner()).isPresent()) {
			throw new BeanExistsException("Owner not exists: " + entryInsertUpdateDTO.getOwner());
		}

		Entry entry = insertDtoToEntity(entryInsertUpdateDTO);

		entry.verifyAttributes();
	}

	@Override
	@Transactional
	public EntryDTO insert(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		validate(entryInsertUpdateDTO);
		return super.insert(entryInsertUpdateDTO);
	}

	@Override
	public Optional<EntryDTO> update(Long updateKey, EntryInsertUpdateDTO entryInsertUpdateDTO) {
		validate(entryInsertUpdateDTO);
		return super.update(updateKey, entryInsertUpdateDTO);
	}

	private Owner owner(OwnerDTO ownerDTO) {
		return this.ownerRepository.findByName(ownerDTO.getName()).get();
	}

	private EquityAccount equityAccount(EquityAccountDTO equityAccountDTO) {
		return this.equityAccountRepository.findByDescription(equityAccountDTO.getDescription()).get();
	}

	public BigDecimal creditSum(OwnerDTO ownerDTO, EquityAccountDTO equityAccountDTO) {
		return this.entryRepository.creditSum(owner(ownerDTO), equityAccount(equityAccountDTO)).getValue();
	}

	public BigDecimal debitSum(OwnerDTO ownerDTO, EquityAccountDTO equityAccountDTO) {
		return this.entryRepository.debitSum(owner(ownerDTO), equityAccount(equityAccountDTO)).getValue();
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

}
