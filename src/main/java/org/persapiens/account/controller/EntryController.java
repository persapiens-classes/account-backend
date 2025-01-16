package org.persapiens.account.controller;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.service.AccountService;
import org.persapiens.account.service.EntryService;
import org.persapiens.account.service.EquityAccountService;
import org.persapiens.account.service.OwnerService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/entries")
public class EntryController extends CrudController<EntryInsertUpdateDTO, EntryDTO, Entry, Long> {

	private EntryService entryService;

	private OwnerService ownerService;

	private AccountService<Account> accountService;

	private EquityAccountService equityAccountService;

	@Override
	protected Entry toEntity(EntryDTO dto) {
		return Entry.builder()
			.inAccount(this.accountService.findByDescription(dto.getInAccount().getDescription()).get())
			.outAccount(this.accountService.findByDescription(dto.getOutAccount().getDescription()).get())
			.owner(this.ownerService.findByName(dto.getOwner()).get())
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
			.inAccount(this.accountService.findByDescription(entryInsertDTO.getInAccount()).get())
			.outAccount(this.accountService.findByDescription(entryInsertDTO.getOutAccount()).get())
			.owner(this.ownerService.findByName(entryInsertDTO.getOwner()).get())
			.value(entryInsertDTO.getValue())
			.note(entryInsertDTO.getNote())
			.date(entryInsertDTO.getDate())
			.build();
	}

	protected Entry updateDtoToEntity(EntryInsertUpdateDTO entryUpdateDTO) {
		return Entry.builder()
			.inAccount(this.accountService.findByDescription(entryUpdateDTO.getInAccount()).get())
			.outAccount(this.accountService.findByDescription(entryUpdateDTO.getOutAccount()).get())
			.owner(this.ownerService.findByName(entryUpdateDTO.getOwner()).get())
			.value(entryUpdateDTO.getValue())
			.note(entryUpdateDTO.getNote())
			.date(entryUpdateDTO.getDate())
			.build();
	}

	@GetMapping("/{id}")
	public Optional<EntryDTO> findById(@PathVariable Long id) {
		return toDTOOptional(this.entryService.findById(id));
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.entryService.deleteById(id);
	}

	@PutMapping("/{id}")
	public EntryDTO update(@PathVariable Long id, @RequestBody EntryInsertUpdateDTO dto) {
		EntryDTO result = null;
		Optional<Entry> entryOptional = this.entryService.findById(id);
		if (entryOptional.isPresent()) {
			Entry currentEntry = entryOptional.get();
			Entry entityToSave = updateDtoToEntity(dto);
			entityToSave.setId(currentEntry.getId());
			Entry saved = this.entryService.save(entityToSave);
			result = toDTO(saved);
		}
		return result;
	}

	@GetMapping("/creditSum")
	public BigDecimal creditSum(String owner, String equityAccount) {
		return this.entryService.creditSum(this.ownerService.findByName(owner).get(),
				this.equityAccountService.findByDescription(equityAccount).get());
	}

	@GetMapping("/debitSum")
	public BigDecimal debitSum(String owner, String equityAccount) {
		return this.entryService.debitSum(this.ownerService.findByName(owner).get(),
				this.equityAccountService.findByDescription(equityAccount).get());
	}

}
