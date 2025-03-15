package org.persapiens.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public abstract class CrudService<I extends Object, U extends Object, F extends Object, B extends Object, E extends Object, K extends Object> {

	private CrudRepository<E, K> repository;

	protected CrudService(CrudRepository<E, K> repository) {
		this.repository = repository;
	}

	protected abstract F toDTO(E entity);

	protected abstract E insertDtoToEntity(I insertDTO);

	protected abstract E updateDtoToEntity(U updateDTO);

	protected abstract E findByUpdateKey(B updateKey);

	protected abstract E setIdToUpdate(E t, E updateEntity);

	protected Iterable<F> toDTOs(Iterable<? extends E> entities) {
		List<F> result = new ArrayList<>();
		for (E entity : entities) {
			result.add(toDTO(entity));
		}
		return result;
	}

	@Transactional
	public F insert(I insertDto) {
		return toDTO(this.repository.save(insertDtoToEntity(insertDto)));
	}

	@Transactional
	public F update(B updateKey, U updateDto) {
		E currentEntity = findByUpdateKey(updateKey);
		E updatedEntity = updateDtoToEntity(updateDto);
		updatedEntity = setIdToUpdate(currentEntity, updatedEntity);
		return toDTO(this.repository.save(updatedEntity));
	}

	public Iterable<F> findAll() {
		return toDTOs(this.repository.findAll());
	}

}
