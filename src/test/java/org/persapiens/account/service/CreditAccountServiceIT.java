package org.persapiens.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.persistence.CategoryFactory;
import org.persapiens.account.persistence.CreditAccountFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CreditAccountServiceIT {

	@Autowired
	private CreditAccountService creditAccountService;

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Autowired
	private CategoryFactory categoryFactory;

	@Test
	public void repositoryNotNull() {
		assertThat(this.creditAccountService).isNotNull();
	}

	@Test
	public void saveOne() {
		// create test environment
		CreditAccount creditAccount = this.creditAccountFactory.internship();

		// verify the results
		assertThat(this.creditAccountService.findById(creditAccount.getId()).get()).isEqualTo(creditAccount);
	}

	@Test
	public void deleteOne() {
		// create test environment
		CreditAccount creditAccount = this.creditAccountFactory.creditAccount("UNIQUE CreditAccount",
				this.categoryFactory.bank());

		// execute the operation to be tested
		this.creditAccountService.delete(creditAccount);

		// verify the results
		assertThat(this.creditAccountService.findById(creditAccount.getId()).isPresent()).isFalse();
	}

}
