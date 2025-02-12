package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
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
	protected Entry toEntity(EntryDTO dto) {
		return Entry.builder()
			.inAccount(this.accountRepository.findByDescription(dto.getInAccount().getDescription()).get())
			.outAccount(this.accountRepository.findByDescription(dto.getOutAccount().getDescription()).get())
			.owner(this.ownerRepository.findByName(dto.getOwner()).get())
			.date(dto.getDate())
			.value(dto.getValue())
			.note(dto.getNote())
			.build();
	}

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

	@Override
	protected Entry insertDtoToEntity(EntryInsertUpdateDTO entryInsertDTO) {
		return Entry.builder()
			.inAccount(this.accountRepository.findByDescription(entryInsertDTO.getInAccount()).get())
			.outAccount(this.accountRepository.findByDescription(entryInsertDTO.getOutAccount()).get())
			.owner(this.ownerRepository.findByName(entryInsertDTO.getOwner()).get())
			.value(entryInsertDTO.getValue())
			.note(entryInsertDTO.getNote())
			.date(entryInsertDTO.getDate())
			.build();
	}

	@Override
	protected Entry updateDtoToEntity(EntryInsertUpdateDTO entryInsertDTO) {
		return insertDtoToEntity(entryInsertDTO);
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

	@Override
	@Transactional
	public EntryDTO insert(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		Entry entry = insertDtoToEntity(entryInsertUpdateDTO);

		entry.verifyAttributes();

		return super.insert(entryInsertUpdateDTO);
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

}
