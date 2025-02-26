package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.dto.EquityAccountDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EquityAccountRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String description = "Super bank account";
		String categoryDescription = category(CategoryConstants.BANK).description();

		EquityAccountDTO equityAccount = new EquityAccountDTO(description, categoryDescription);

		var equityAccountRestClient = equityAccountRestClient();
		// verify insert operation
		assertThat(equityAccountRestClient.insert(equityAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(equityAccountRestClient.findByDescription(description).description())
			.isEqualTo(equityAccount.description());

		// verify findAll operation
		assertThat(equityAccountRestClient.findAll()).isNotEmpty();
	}

	@Test
	void updateOne() {
		EquityAccountDTO equityAccount = equityAccount("Inserted equityAccount",
				category(CategoryConstants.BANK).description());

		String originalDescription = equityAccount.description();
		equityAccount = new EquityAccountDTO("Updated equityAccount", equityAccount.category());

		var equityAccountRestClient = equityAccountRestClient();
		equityAccount = equityAccountRestClient.update(originalDescription, equityAccount);

		// verify update operation
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> equityAccountRestClient.findByDescription(originalDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));

		// verify update operation
		assertThat(equityAccountRestClient.findByDescription(equityAccount.description()).description())
			.isEqualTo(equityAccount.description());
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic equityAccount";

		var equityAccountRestClient = equityAccountRestClient();
		equityAccountRestClient
			.insert(new EquityAccountDTO(description, category(CategoryConstants.BANK).description()));
		assertThat(equityAccountRestClient.findByDescription(description).description()).isEqualTo(description);

		// execute deleteByName operation
		equityAccountRestClient.deleteByDescription(description);
		// verify the results
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> equityAccountRestClient.findByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalid(String description, HttpStatus httpStatus) {
		var equityAccountRestClient = equityAccountRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> equityAccountRestClient.deleteByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String description = "Invalid equity account";
		deleteInvalid("", HttpStatus.FORBIDDEN);
		deleteInvalid(description, HttpStatus.NOT_FOUND);
	}

}
