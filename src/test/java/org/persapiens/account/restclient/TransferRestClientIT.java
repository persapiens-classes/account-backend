package org.persapiens.account.restclient;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.TransferDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferRestClientIT extends RestClientIT {

	private TransferRestClient transferRestClient() {
		return TransferRestClient.builder().restClientHelper(this.<TransferDTO>restClientHelper("")).build();
	}

	@Test
	void transfer50FromCheckingsAuntToInvestimentUncle() {
		OwnerDTO aunt = owner(OwnerConstants.AUNT);
		OwnerDTO uncle = owner(OwnerConstants.UNCLE);

		EquityAccountDTO checkings = equityAccount(EquityAccountConstants.CHECKING,
				category(CategoryConstants.BANK).getDescription());
		EquityAccountDTO investiment = equityAccount(EquityAccountConstants.INVESTIMENT,
				category(CategoryConstants.BANK).getDescription());

		// execute transfer operation
		transferRestClient().transfer(TransferDTO.builder()
			.debitOwner(aunt.getName())
			.creditOwner(uncle.getName())
			.debitAccount(checkings.getDescription())
			.creditAccount(investiment.getDescription())
			.value(new BigDecimal(50))
			.build());

		assertThat(entryRestClient().debitSum(aunt.getName(), checkings.getDescription()))
			.isEqualTo(new BigDecimal(50).setScale(2));

		assertThat(entryRestClient().creditSum(uncle.getName(), investiment.getDescription()))
			.isEqualTo(new BigDecimal(50).setScale(2));
	}

	@Test
	void transferInvalid() {
		String aunt = owner(OwnerConstants.AUNT).getName();
		String uncle = owner(OwnerConstants.UNCLE).getName();

		String checkings = equityAccount(EquityAccountConstants.CHECKING,
				category(CategoryConstants.BANK).getDescription())
			.getDescription();
		String investiment = equityAccount(EquityAccountConstants.INVESTIMENT,
				category(CategoryConstants.BANK).getDescription())
			.getDescription();

		BigDecimal value = new BigDecimal(100);

		// test blank fields
		transferInvalid("", checkings, uncle, investiment, value, HttpStatus.BAD_REQUEST);
		transferInvalid(aunt, "", uncle, investiment, value, HttpStatus.BAD_REQUEST);
		transferInvalid(aunt, checkings, "", investiment, value, HttpStatus.BAD_REQUEST);
		transferInvalid(aunt, checkings, uncle, "", value, HttpStatus.BAD_REQUEST);
		transferInvalid(aunt, checkings, uncle, investiment, null, HttpStatus.BAD_REQUEST);

		// test fields
		transferInvalid("invalid debit owner", checkings, uncle, investiment, value, HttpStatus.CONFLICT);
		transferInvalid(aunt, "invalid debit equity account", uncle, investiment, value, HttpStatus.CONFLICT);
		transferInvalid(aunt, checkings, "invalid credit account", investiment, value, HttpStatus.CONFLICT);
		transferInvalid(aunt, checkings, uncle, "invalid credit equity account", value, HttpStatus.CONFLICT);

		// test same owners
		transferInvalid(aunt, checkings, aunt, investiment, value, HttpStatus.BAD_REQUEST);
	}

	private void transferInvalid(String debitOwnerName, String debitEquityAccountDescription, String creditOwnerName,
			String creditEquityAccountDescription, BigDecimal value, HttpStatus httpStatus) {
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> transferRestClient().transfer(TransferDTO.builder()
				.debitOwner(debitOwnerName)
				.debitAccount(debitEquityAccountDescription)
				.creditOwner(creditOwnerName)
				.creditAccount(creditEquityAccountDescription)
				.value(value)
				.build()))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

}
