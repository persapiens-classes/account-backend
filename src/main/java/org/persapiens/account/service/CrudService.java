package org.persapiens.account.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class CrudService<T extends Object, I> {

	private CrudRepository<T, I> repository;

	@Autowired
	public void setRepository(CrudRepository<T, I> repository) {
		this.repository = repository;
	}

	@Transactional
	public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		return this.repository.saveAll(entities);
	}

	@Transactional
	public <S extends T> S save(S entity) {
		return this.repository.save(entity);
	}

	@Transactional
	public void deleteAll(Iterable<? extends T> entities) {
		this.repository.deleteAll(entities);
	}

	@Transactional
	public void deleteAllById(Iterable<? extends I> ids) {
		this.repository.deleteAllById(ids);
	}

	@Transactional
	public void deleteById(I id) {
		this.repository.deleteById(id);
	}

	@Transactional
	public void delete(T entity) {
		this.repository.delete(entity);
	}

	@Transactional
	public void deleteAll() {
		this.repository.deleteAll();
	}

	public Optional<T> findById(I id) {
		return this.repository.findById(id);
	}

	public Iterable<T> findAll() {
		return this.repository.findAll();
	}

	public Iterable<T> findAllById(Iterable<I> ids) {
		return this.repository.findAllById(ids);
	}

	public long count() {
		return this.repository.count();
	}

	public boolean existsById(I id) {
		return this.repository.existsById(id);
	}

}
