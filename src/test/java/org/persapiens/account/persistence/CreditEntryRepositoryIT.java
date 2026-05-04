package org.persapiens.account.persistence;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationTest;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class CreditEntryRepositoryIT {

	@Autowired
	private CreditEntryFactory entryFactory;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private CreditEntryRepository creditEntryRepository;

	@Test
	void repositoryNaoEhNulo() {
		assertThat(this.creditEntryRepository).isNotNull();
	}

	@Test
	void credit300() {
		// create test environment
		Owner father = this.ownerFactory.father();

		EquityAccount savings = this.equityAccountFactory.savings();

		CreditAccount internship = this.creditAccountFactory.internship();

		this.entryFactory.entry(father, savings, internship, new BigDecimal(300));

		// execute the operation to be tested
		// verify the results
		assertThat(this.creditEntryRepository.creditSum(father, savings)).isEqualTo(new BigDecimal(300).setScale(2));
	}

}
