package org.persapiens.account.controller;

import jakarta.validation.Valid;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.service.EntryService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EntryController<Z extends Entry<Z, N, J, O, P>, N extends Account<J>, J extends Category, O extends Account<P>, P extends Category>
		extends CrudController<EntryInsertUpdateDTO, EntryInsertUpdateDTO, EntryDTO, Long, Z, Long> {

	private EntryService<Z, N, J, O, P> entryService;

	public EntryController(EntryService<Z, N, J, O, P> entryService) {
		super(entryService);

		this.entryService = entryService;
	}

	@FindOneBeanDoc
	@GetMapping("/{id}")
	public EntryDTO findById(@PathVariable(required = true) Long id) {
		return this.entryService.findById(id);
	}

	@DeleteBeanDoc
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable(required = true) Long id) {
		this.entryService.deleteById(id);
	}

	@UpdateBeanDoc
	@PutMapping("/{id}")
	public EntryDTO update(@PathVariable(required = true) Long id,
			@Valid @RequestBody(required = true) EntryInsertUpdateDTO entryDTO) {
		return this.entryService.update(id, entryDTO);
	}

}
