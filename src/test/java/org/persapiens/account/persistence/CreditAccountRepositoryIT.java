package org.persapiens.account.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.Category;
import org.persapiens.account.domain.CreditAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.persapiens.account.common.CategoryConstants.SALARY;
import static org.persapiens.account.common.CreditAccountConstants.INTERNSHIP;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CreditAccountRepositoryIT {

	@Autowired
	private CreditAccountRepository creditAccountRepository;

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Test
	public void repositoryNotNull() {
		assertThat(this.creditAccountRepository).isNotNull();
	}

	@Test
	public void findOneByExample() {
		// create test environment
		CreditAccount creditAccount = this.creditAccountFactory.internship();

		CreditAccount creditAccountExemplo = CreditAccount.builder()
			.description(INTERNSHIP)
			.category(Category.builder().description(SALARY).build())
			.build();

		// execute the operation to be tested
		// verify the results
		assertThat(this.creditAccountRepository.findOne(Example.of(creditAccountExemplo)).get())
			.isEqualTo(creditAccount);
	}

}
