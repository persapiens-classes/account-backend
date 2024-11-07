package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.dto.DebitAccountDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DebitAccountRestClientIT extends RestClientIT {

	@Test
	void saveOne() {
		String description = "Uber";
		String categoryDescription = CategoryConstants.TRANSPORT;

		DebitAccountDTO debitAccount = DebitAccountDTO.builder()
			.description(description)
			.category(category(categoryDescription))
			.build();

		// verify save operation
		assertThat(debitAccountRestClient().save(debitAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(debitAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(debitAccount.getDescription());

		// verify findAll operation
		assertThat(debitAccountRestClient().findAll()).isNotEmpty();
	}

}
