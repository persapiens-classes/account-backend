package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.dto.EquityAccountDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EquityAccountRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String description = "Super bank account";
		String categoryDescription = category(CategoryConstants.BANK).getDescription();

		EquityAccountDTO equityAccount = EquityAccountDTO.builder()
			.description(description)
			.category(categoryDescription)
			.build();

		// verify insert operation
		assertThat(equityAccountRestClient().insert(equityAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(equityAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(equityAccount.getDescription());

		// verify findAll operation
		assertThat(equityAccountRestClient().findAll()).isNotEmpty();
	}

	@Test
	void updateOne() {
		EquityAccountDTO equityAccount = equityAccount("Inserted equityAccount",
				category(CategoryConstants.BANK).getDescription());

		String originalDescription = equityAccount.getDescription();
		equityAccount.setDescription("Updated equityAccount");

		equityAccountRestClient().update(originalDescription, equityAccount);

		// verify update operation
		assertThat(equityAccountRestClient().findByDescription(originalDescription)).isEmpty();

		// verify update operation
		assertThat(equityAccountRestClient().findByDescription(equityAccount.getDescription()).get().getDescription())
			.isEqualTo(equityAccount.getDescription());
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic equityAccount";

		equityAccountRestClient().insert(EquityAccountDTO.builder()
			.description(description)
			.category(category(CategoryConstants.BANK).getDescription())
			.build());
		assertThat(equityAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(description);

		// execute deleteByName operation
		equityAccountRestClient().deleteByDescription(description);
		// verify the results
		assertThat(equityAccountRestClient().findByDescription(description)).isEmpty();
	}

}
