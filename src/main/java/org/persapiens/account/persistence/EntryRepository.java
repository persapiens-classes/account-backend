package org.persapiens.account.persistence;

import org.persapiens.account.domain.Account;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.Entry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntryRepository <T extends Entry<I, D, O, E>, I extends Account<D>, D extends Category, O extends Account<E>, E extends Category>
    extends CrudRepository<T, Long> {

}
