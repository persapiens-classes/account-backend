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
		return new EntryDTO(entry.getId(), entry.getOwner().getName(), entry.getDate(),
				new AccountDTO(entry.getInAccount().getDescription(),
						entry.getInAccount().getCategory().getDescription()),
				new AccountDTO(entry.getOutAccount().getDescription(),
						entry.getOutAccount().getCategory().getDescription()),
				entry.getValue(), entry.getNote());
	}

	private Entry toEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		return Entry.builder()
			.inAccount(this.accountRepository.findByDescription(entryInsertUpdateDTO.inAccount()).get())
			.outAccount(this.accountRepository.findByDescription(entryInsertUpdateDTO.outAccount()).get())
			.owner(this.ownerRepository.findByName(entryInsertUpdateDTO.owner()).get())
			.value(entryInsertUpdateDTO.value())
			.note(entryInsertUpdateDTO.note())
			.date(entryInsertUpdateDTO.date())
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
		if (StringUtils.isBlank(entryInsertUpdateDTO.inAccount())) {
			throw new IllegalArgumentException("Description empty!");
		}
		if (StringUtils.isBlank(entryInsertUpdateDTO.outAccount())) {
			throw new IllegalArgumentException("Category empty!");
		}
		if (StringUtils.isBlank(entryInsertUpdateDTO.owner())) {
			throw new IllegalArgumentException("Owner empty!");
		}
		if (entryInsertUpdateDTO.value() == null) {
			throw new IllegalArgumentException("Value null!");
		}
		if (entryInsertUpdateDTO.date() == null) {
			throw new IllegalArgumentException("Date null!");
		}
	}

	private void validate(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		validateBlank(entryInsertUpdateDTO);
		if (!this.accountRepository.findByDescription(entryInsertUpdateDTO.inAccount()).isPresent()) {
			throw new BeanExistsException("In account not exists: " + entryInsertUpdateDTO.inAccount());
		}
		if (!this.accountRepository.findByDescription(entryInsertUpdateDTO.outAccount()).isPresent()) {
			throw new AttributeNotFoundException("Out account not exists: " + entryInsertUpdateDTO.outAccount());
		}
		if (!this.ownerRepository.findByName(entryInsertUpdateDTO.owner()).isPresent()) {
			throw new BeanExistsException("Owner not exists: " + entryInsertUpdateDTO.owner());
		}

		Entry entry = insertDtoToEntity(entryInsertUpdateDTO);

		entry.verifyAttributes();
	}

	@Override
	public EntryDTO insert(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		validate(entryInsertUpdateDTO);
		return super.insert(entryInsertUpdateDTO);
	}

	@Override
	public EntryDTO update(Long updateKey, EntryInsertUpdateDTO entryInsertUpdateDTO) {
		validate(entryInsertUpdateDTO);
		return super.update(updateKey, entryInsertUpdateDTO);
	}

	public BigDecimal creditSum(String ownerName, String equityAccountDescription) {
		Optional<Owner> byName = this.ownerRepository.findByName(ownerName);
		Optional<EquityAccount> byDescription = this.equityAccountRepository
			.findByDescription(equityAccountDescription);

		return this.entryRepository.creditSum(byName.get(), byDescription.get()).getValue();
	}

	public BigDecimal debitSum(String ownerName, String equityAccountDescription) {
		Optional<Owner> byName = this.ownerRepository.findByName(ownerName);
		Optional<EquityAccount> byDescription = this.equityAccountRepository
			.findByDescription(equityAccountDescription);

		return this.entryRepository.debitSum(byName.get(), byDescription.get()).getValue();
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
		Optional<Entry> byId = this.entryRepository.findById(id);
		if (byId.isPresent()) {
			return toDTO(byId.get());
		}
		else {
			throw new BeanNotFoundException("Entry not found by: " + id);
		}
	}

}
