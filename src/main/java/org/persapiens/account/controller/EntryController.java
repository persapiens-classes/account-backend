package org.persapiens.account.controller;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.service.EntryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/entries")
public class EntryController
		extends CrudController<EntryInsertUpdateDTO, EntryInsertUpdateDTO, EntryDTO, Long, Entry, Long> {

	private EntryService entryService;

	@GetMapping("/{id}")
	public EntryDTO findById(@PathVariable(required = true) Long id) {
		return this.entryService.findById(id);
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable(required = true) Long id) {
		this.entryService.deleteById(id);
	}

	@PutMapping("/{id}")
	public EntryDTO update(@PathVariable(required = true) Long id,
			@Valid @RequestBody(required = true) EntryInsertUpdateDTO entryDTO) {
		return this.entryService.update(id, entryDTO);
	}

	@GetMapping("/creditSum")
	public BigDecimal creditSum(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.entryService.creditSum(owner, equityAccount);
	}

	@GetMapping("/debitSum")
	public BigDecimal debitSum(@RequestParam(required = true) String owner,
			@RequestParam(required = true) String equityAccount) {
		return this.entryService.debitSum(owner, equityAccount);
	}

}
