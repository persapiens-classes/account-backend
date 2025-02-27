package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.dto.DebitAccountDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DebitAccountRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String description = "Uber";
		String categoryDescription = category(CategoryConstants.TRANSPORT).description();

		DebitAccountDTO debitAccount = new DebitAccountDTO(description, categoryDescription);

		var debitAccountRestClient = debitAccountRestClient();

		// verify insert operation
		assertThat(debitAccountRestClient.insert(debitAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(debitAccountRestClient.findByDescription(description).description())
			.isEqualTo(debitAccount.description());

		// verify findAll operation
		assertThat(debitAccountRestClient.findAll()).isNotEmpty();
	}

	@Test
	void updateOne() {
		DebitAccountDTO debitAccount = debitAccount("Inserted debitAccount",
				category(CategoryConstants.TRANSPORT).description());

		String originalDescription = debitAccount.description();
		debitAccount = new DebitAccountDTO("Updated debitAccount", debitAccount.category());

		var debitAccountRestClient = debitAccountRestClient();

		debitAccount = debitAccountRestClient.update(originalDescription, debitAccount);

		// verify update operation
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitAccountRestClient.findByDescription(originalDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));

		// verify update operation
		assertThat(debitAccountRestClient.findByDescription(debitAccount.description()).description())
			.isEqualTo(debitAccount.description());
	}

	@Test
	void deleteOne() {
		// create test environment
		String description = "Fantastic debitAccount";

		var debitAccountRestClient = debitAccountRestClient();
		debitAccountRestClient
			.insert(new DebitAccountDTO(description, category(CategoryConstants.TRANSPORT).description()));
		assertThat(debitAccountRestClient.findByDescription(description).description()).isEqualTo(description);

		// execute deleteByDescription operation
		debitAccountRestClient.deleteByDescription(description);
		// verify the results
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitAccountRestClient.findByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalidDebitAccount(String description, HttpStatus httpStatus) {
		var debitAccountRestClient = debitAccountRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> debitAccountRestClient.deleteByDescription(description))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String description = "Invalid debit account";
		deleteInvalidDebitAccount("", HttpStatus.FORBIDDEN);
		deleteInvalidDebitAccount(description, HttpStatus.NOT_FOUND);
	}

}
