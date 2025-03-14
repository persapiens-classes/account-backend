package org.persapiens.account.persistence;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
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

	@BeforeEach
	void deleteAll() {
		this.debitEntryRepository.deleteAll();
		assertThat(this.debitEntryRepository.findAll()).isEmpty();
	}

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
		assertThat(this.debitEntryRepository.debitSum(father, savings).getValue())
			.isEqualTo(new BigDecimal(500).setScale(2));
	}

}
