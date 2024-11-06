package org.persapiens.account.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.persistence.CreditAccountFactory;
import org.persapiens.account.persistence.DebitAccountFactory;
import org.persapiens.account.persistence.EntryFactory;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.OwnerEquityAccountInitialValueFactory;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BalanceServiceIT {

	@Autowired
	private EntryFactory entryFactory;

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private BalanceService balanceService;

	@Autowired
	private OwnerEquityAccountInitialValueFactory ownerEquityAccountInitialValueFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Test
	public void balance800() {
		// create test environment
		Owner father = this.ownerFactory.father();

		EquityAccount wallet = this.equityAccountFactory.wallet();

		DebitAccount gasoline = this.debitAccountFactory.gasoline();

		CreditAccount internship = this.creditAccountFactory.internship();

		this.ownerEquityAccountInitialValueFactory.ownerEquityAccountInitialValue(father, wallet, new BigDecimal(1000));

		this.entryFactory.entry(father, gasoline, wallet, new BigDecimal(500));
		this.entryFactory.entry(father, wallet, internship, new BigDecimal(300));

		// run the operation to be tested
		// verify the results
		assertThat(this.balanceService.balance(father, wallet)).isEqualTo(new BigDecimal(800).setScale(2));
	}

}
