package org.persapiens.account.controller;

import java.util.List;
import java.util.stream.StreamSupport;

import jakarta.validation.Valid;
import org.persapiens.account.service.CrudService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class CrudController<I extends Object, U extends Object, F extends Object, B extends Object, E extends Object, K extends Object> {

	private CrudService<I, U, F, B, E, K> crudService;

	@Autowired
	public void setService(CrudService<I, U, F, B, E, K> service) {
		this.crudService = service;
	}

	@PostMapping
	public F insert(@Valid @RequestBody(required = true) I insertDto) {
		return this.crudService.insert(insertDto);
	}

	@GetMapping
	public List<F> findAll() {
		return StreamSupport.stream(this.crudService.findAll().spliterator(), false).toList();
	}

}
