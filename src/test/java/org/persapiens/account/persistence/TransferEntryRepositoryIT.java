package org.persapiens.account.persistence;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TransferEntryRepositoryIT {

	@Autowired
	private TransferEntryFactory entryFactory;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private TransferEntryRepository transferEntryRepository;

	@BeforeEach
	void deleteAll() {
		this.transferEntryRepository.deleteAll();
		assertThat(this.transferEntryRepository.findAll()).isEmpty();
	}

	@Test
	void repositoryNaoEhNulo() {
		assertThat(this.transferEntryRepository).isNotNull();
	}

	@Test
	void credit300() {
		// create test environment
		Owner father = this.ownerFactory.father();

		EquityAccount savings = this.equityAccountFactory.savings();

		EquityAccount internship = this.equityAccountFactory.checking();

		this.entryFactory.entry(father, father, savings, internship, new BigDecimal(300));

		// execute the operation to be tested
		// verify the results
		assertThat(this.transferEntryRepository.creditSum(father, savings)).isEqualTo(new BigDecimal(300).setScale(2));
	}

	@Test
	void debit500() {
		// create test environment
		Owner father = this.ownerFactory.father();

		EquityAccount savings = this.equityAccountFactory.savings();

		EquityAccount gasoline = this.equityAccountFactory.investment();

		this.entryFactory.entry(father, father, gasoline, savings, new BigDecimal(500));

		// execute the operation to be tested
		// verify the results
		assertThat(this.transferEntryRepository.debitSum(father, savings)).isEqualTo(new BigDecimal(500).setScale(2));
	}

}
