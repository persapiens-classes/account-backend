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
		return TransferRestClient.builder()
			.restClientHelper(this.<TransferDTO>authenticatedRestClientHelper(""))
			.build();
	}

	@Test
	void transfer50FromCheckingsAuntToInvestimentUncle() {
		OwnerDTO aunt = owner(OwnerConstants.AUNT);
		OwnerDTO uncle = owner(OwnerConstants.UNCLE);

		EquityAccountDTO checkings = equityAccount(EquityAccountConstants.CHECKING,
				category(CategoryConstants.BANK).description());
		EquityAccountDTO investiment = equityAccount(EquityAccountConstants.INVESTIMENT,
				category(CategoryConstants.BANK).description());

		var transferRestClient = transferRestClient();
		// execute transfer operation
		transferRestClient.transfer(new TransferDTO(aunt.name(), checkings.description(), uncle.name(),
				investiment.description(), new BigDecimal(50)));

		var entryRestClient = entryRestClient();
		assertThat(entryRestClient.debitSum(aunt.name(), checkings.description()))
			.isEqualTo(new BigDecimal(50).setScale(2));

		assertThat(entryRestClient.creditSum(uncle.name(), investiment.description()))
			.isEqualTo(new BigDecimal(50).setScale(2));
	}

	@Test
	void transferInvalid() {
		String aunt = owner(OwnerConstants.AUNT).name();
		String uncle = owner(OwnerConstants.UNCLE).name();

		String checkings = equityAccount(EquityAccountConstants.CHECKING,
				category(CategoryConstants.BANK).description())
			.description();
		String investiment = equityAccount(EquityAccountConstants.INVESTIMENT,
				category(CategoryConstants.BANK).description())
			.description();

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
		var transferDTO = new TransferDTO(debitOwnerName, debitEquityAccountDescription, creditOwnerName,
				creditEquityAccountDescription, value);
		var transferRestClient = transferRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> transferRestClient.transfer(transferDTO))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

}
