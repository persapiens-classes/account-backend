package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.domain.Category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface CategoryRepository<T extends Category> extends CrudRepository<T, Long> {

	Optional<T> findByDescription(String description);

	@Transactional
	long deleteByDescription(String description);

}
