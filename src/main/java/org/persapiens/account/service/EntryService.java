package org.persapiens.account.service;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.persistence.CreditAccountRepository;
import org.persapiens.account.persistence.DebitAccountRepository;
import org.persapiens.account.persistence.EntryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class EntryService extends CrudService<EntryInsertUpdateDTO, EntryInsertUpdateDTO, EntryDTO, Long, Entry, Long> {

	private EntryRepository entryRepository;

	private CreditAccountRepository creditAccountRepository;

	private DebitAccountRepository debitAccountRepository;

	private EquityAccountService equityAccountService;

	private OwnerService ownerService;

	@Override
	protected EntryDTO toDTO(Entry entry) {
		return new EntryDTO(entry.getId(), entry.getOwner().getName(), entry.getDate(),
				new AccountDTO(entry.getInAccount().getDescription(),
						entry.getInAccount().getCategory().getDescription()),
				new AccountDTO(entry.getOutAccount().getDescription(),
						entry.getOutAccount().getCategory().getDescription()),
				entry.getValue(), entry.getNote());
	}

	private Account inAccount(String accountDescription) {
		Optional<DebitAccount> byDescription = this.debitAccountRepository.findByDescription(accountDescription);
		if (byDescription.isPresent()) {
			return byDescription.get();
		}
		else {
			return this.equityAccountService.findEntityByDescription(accountDescription);
		}
	}

	private Account outAccount(String accountDescription) {
		Optional<CreditAccount> byDescription = this.creditAccountRepository.findByDescription(accountDescription);
		if (byDescription.isPresent()) {
			return byDescription.get();
		}
		else {
			return this.equityAccountService.findEntityByDescription(accountDescription);
		}
	}

	private Entry toEntity(EntryInsertUpdateDTO entryInsertUpdateDTO) {
		Owner owner = this.ownerService.findEntityByName(entryInsertUpdateDTO.owner());
		Account inAccount = inAccount(entryInsertUpdateDTO.inAccount());
		Account outAccount = outAccount(entryInsertUpdateDTO.outAccount());

		return Entry.builder()
			.inAccount(inAccount)
			.outAccount(outAccount)
			.owner(owner)
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

	Entry findEntityById(Long id) {
		Optional<Entry> byId = this.entryRepository.findById(id);
		if (byId.isPresent()) {
			return byId.get();
		}
		else {
			throw new BeanNotFoundException("Entry not found by: " + id);
		}
	}

	@Override
	protected Entry findByUpdateKey(Long updateKey) {
		return findEntityById(updateKey);
	}

	@Override
	protected Entry setIdToUpdate(Entry t, Entry updateEntity) {
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

	public BigDecimal creditSum(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		return this.entryRepository.creditSum(owner, equityAccount).getValue();
	}

	public BigDecimal debitSum(String ownerName, String equityAccountDescription) {
		Owner owner = this.ownerService.findEntityByName(ownerName);
		EquityAccount equityAccount = this.equityAccountService.findEntityByDescription(equityAccountDescription);

		return this.entryRepository.debitSum(owner, equityAccount).getValue();
	}

}
