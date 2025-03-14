package org.persapiens.account.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.service.EntryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
public class EntryController<E extends Entry<E, I, J, O, P>, I extends Account<J>, J extends Category, O extends Account<P>, P extends Category>
		extends CrudController<EntryInsertUpdateDTO, EntryInsertUpdateDTO, EntryDTO, Long, E, Long> {

	private EntryService<E, I, J, O, P> entryService;

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

}
