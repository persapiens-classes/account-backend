package org.persapiens.account.persistence;

import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.Entry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntryRepository <E extends Entry<E, I, J, O, P>, I extends Account<J>, J extends Category, O extends Account<P>, P extends Category> 
	extends CrudRepository<E, Long> {

}
