package org.persapiens.account.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.persapiens.account.service.CrudService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class CrudController<D extends Object, T extends Object, I extends Serializable> {

	private CrudService<T, I> crudService;

	@Autowired
	public void setService(CrudService<T, I> service) {
		this.crudService = service;
	}

	@PostMapping
	public D save(@RequestBody D dto) {
		T saved = this.crudService.save(toEntity(dto));
		return toDTO(saved);
	}

	@GetMapping
	public Iterable<D> findAll() {
		List<D> result = new ArrayList<>();
		for (T entity : this.crudService.findAll()) {
			result.add(toDTO(entity));
		}
		return result;
	}

	public Optional<D> toDTOOptional(Optional<T> entity) {
		if (entity.isEmpty()) {
			return Optional.empty();
		}
		else {
			return Optional.of(toDTO(entity.get()));
		}
	}

	protected abstract T toEntity(D dto);

	protected abstract D toDTO(T entity);

}
