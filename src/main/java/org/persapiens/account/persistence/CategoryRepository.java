package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.domain.Category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByDescription(String description);

	@Transactional
	long deleteByDescription(String description);

}
