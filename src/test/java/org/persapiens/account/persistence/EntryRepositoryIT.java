package org.persapiens.account.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EntryRepositoryIT {

	@Autowired
	private EntryFactory entryFactory;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private EntryRepository entryRepository;

	@BeforeEach
	public void deleteAll() {
		this.entryRepository.deleteAll();
		assertThat(this.entryRepository.findAll())
			.isEmpty();
	}

	@Test
	public void repositoryNaoEhNulo() {
		assertThat(this.entryRepository)
			.isNotNull();
	}

	@Test
	public void credit300() {
		// create test environment
		Owner father = this.ownerFactory.father();

		EquityAccount savings = this.equityAccountFactory.savings();

		CreditAccount internship = this.creditAccountFactory.internship();

		this.entryFactory.entry(father, savings, internship, new BigDecimal(300));

		// execute the operation to be tested
		// verify the results
		assertThat(this.entryRepository.creditSum(father, savings).getValue())
				.isEqualTo(new BigDecimal(300).setScale(2));
	}

	@Test
	public void debit500() {
		// create test environment
		Owner father = this.ownerFactory.father();

		EquityAccount savings = this.equityAccountFactory.savings();

		DebitAccount gasoline = this.debitAccountFactory.gasoline();

		this.entryFactory.entry(father, gasoline, savings, new BigDecimal(500));

		// execute the operation to be tested
		// verify the results
		assertThat(this.entryRepository.debitSum(father, savings).getValue())
			.isEqualTo(new BigDecimal(500).setScale(2));
	}
}
