package org.persapiens.account.persistence;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationTest;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class DebitEntryRepositoryIT {

	@Autowired
	private DebitEntryFactory entryFactory;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private DebitEntryRepository debitEntryRepository;

	@Test
	void repositoryNaoEhNulo() {
		assertThat(this.debitEntryRepository).isNotNull();
	}

	@Test
	void debit500() {
		// create test environment
		Owner father = this.ownerFactory.father();

		EquityAccount savings = this.equityAccountFactory.savings();

		DebitAccount gasoline = this.debitAccountFactory.gasoline();

		this.entryFactory.entry(father, gasoline, savings, new BigDecimal(500));

		// execute the operation to be tested
		// verify the results
		assertThat(this.debitEntryRepository.debitSum(father, savings))
			.isEqualTo(new BigDecimal(500).setScale(2));
	}

}
