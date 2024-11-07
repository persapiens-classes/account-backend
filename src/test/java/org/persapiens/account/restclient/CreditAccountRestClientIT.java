package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.dto.CreditAccountDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditAccountRestClientIT extends RestClientIT {

	@Test
	public void saveOne() {
		String description = "New job";
		String categoryDescription = CategoryConstants.SALARY;

		CreditAccountDTO creditAccount = CreditAccountDTO.builder()
			.description(description)
			.category(category(categoryDescription))
			.build();

		// verify save operation
		assertThat(creditAccountRestClient().save(creditAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(creditAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(creditAccount.getDescription());

		// verify findAll operation
		assertThat(creditAccountRestClient().findAll()).isNotEmpty();
	}

}
