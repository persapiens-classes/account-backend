package org.persapiens.account.persistence;

import java.util.Optional;

import org.persapiens.account.domain.Category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface CategoryRepository<C extends Category> extends CrudRepository<C, Long> {

	Optional<C> findByDescription(String description);

	@Transactional
	long deleteByDescription(String description);

}
