package org.persapiens.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public abstract class CrudService<I extends Object, U extends Object, F extends Object, B extends Object, E extends Object, K extends Object> {

	private CrudRepository<E, K> repository;

	protected abstract E toEntity(F dto);

	protected abstract F toDTO(E entity);

	protected abstract E insertDtoToEntity(I dto);

	protected abstract E updateDtoToEntity(U dto);

	protected abstract Optional<E> findByUpdateKey(B updateKey);

	protected abstract E setIdToUpdate(E t, E updateEntity);

	protected Optional<F> toOptionalDTO(Optional<E> optionalEntity) {
		Optional<F> result;
		if (optionalEntity.isPresent()) {
			result = Optional.of(toDTO(optionalEntity.get()));
		}
		else {
			result = Optional.empty();
		}
		return result;
	}

	protected Iterable<F> toDTOs(Iterable<? extends E> entities) {
		List<F> result = new ArrayList<>();
		for (E entity : entities) {
			result.add(toDTO(entity));
		}
		return result;
	}

	protected Iterable<E> toEntities(Iterable<? extends F> dtos) {
		List<E> result = new ArrayList<>();
		for (F dto : dtos) {
			result.add(toEntity(dto));
		}
		return result;
	}

	@Autowired
	public void setRepository(CrudRepository<E, K> repository) {
		this.repository = repository;
	}

	@Transactional
	public F insert(I insertDto) {
		return toDTO(this.repository.save(insertDtoToEntity(insertDto)));
	}

	@Transactional
	public Optional<F> update(B updateKey, U updateDto) {
		Optional<E> byUpdateKey = findByUpdateKey(updateKey);
		if (byUpdateKey.isPresent()) {
			E updateEntity = updateDtoToEntity(updateDto);
			updateEntity = setIdToUpdate(byUpdateKey.get(), updateEntity);
			return Optional.of(toDTO(this.repository.save(updateEntity)));
		}
		else {
			return Optional.empty();
		}
	}

	public Optional<F> findById(K id) {
		Optional<F> result;
		Optional<E> byId = this.repository.findById(id);
		if (byId.isPresent()) {
			result = Optional.of(toDTO(byId.get()));
		}
		else {
			result = Optional.empty();
		}
		return result;
	}

	public Iterable<F> findAll() {
		return toDTOs(this.repository.findAll());
	}

	public Iterable<F> findAllById(Iterable<K> ids) {
		return toDTOs(this.repository.findAllById(ids));
	}

	public long count() {
		return this.repository.count();
	}

	public boolean existsById(K id) {
		return this.repository.existsById(id);
	}

}
