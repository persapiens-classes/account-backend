package org.persapiens.account.controller;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
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
public class EntryController
		extends CrudController<EntryInsertUpdateDTO, EntryInsertUpdateDTO, EntryDTO, Long, Entry, Long> {

	private EntryService entryService;

	private OwnerService ownerService;

	private EquityAccountService equityAccountService;

	@GetMapping("/{id}")
	public Optional<EntryDTO> findById(@PathVariable Long id) {
		return this.entryService.findById(id);
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
		this.entryService.deleteById(id);
	}

	@PutMapping("/{id}")
	public EntryDTO update(@PathVariable Long id, @RequestBody EntryInsertUpdateDTO dto) {
		EntryDTO result = null;
		Optional<EntryDTO> entryOptional = this.entryService.update(id, dto);
		if (entryOptional.isPresent()) {
			result = entryOptional.get();
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
