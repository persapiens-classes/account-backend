package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.common.DebitAccountConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BalanceRestClientIT extends RestClientIT {

	private BalanceRestClient balanceRestClient() {
		return BalanceRestClient.builder().restClientHelper(this.<BigDecimal>authenticatedRestClientHelper("")).build();
	}

	@Test
	void balance500() {
		String uncle = owner(OwnerConstants.UNCLE).name();
		String savings = equityAccount(EquityAccountConstants.SAVINGS, category(CategoryConstants.BANK).description())
			.description();

		// initial value 100
		OwnerEquityAccountInitialValueDTO initialValue = OwnerEquityAccountInitialValueDTO.builder()
			.equityAccount(savings)
			.owner(uncle)
			.value(new BigDecimal(100))
			.build();
		ownerEquityAccountInitialValueRestClient().insert(initialValue);

		LocalDateTime date = LocalDateTime.now();

		// credit 600
		String internship = creditAccount(CreditAccountConstants.INTERNSHIP,
				category(CategoryConstants.SALARY).description())
			.description();
		EntryInsertUpdateDTO entryCredito = EntryInsertUpdateDTO.builder()
			.value(new BigDecimal(600))
			.owner(uncle)
			.inAccount(savings)
			.outAccount(internship)
			.date(date)
			.build();
		entryRestClient().insert(entryCredito);

		// debit 200
		String gasoline = debitAccount(DebitAccountConstants.GASOLINE,
				category(CategoryConstants.TRANSPORT).description())
			.description();
		EntryInsertUpdateDTO entryDebito = EntryInsertUpdateDTO.builder()
			.value(new BigDecimal(200))
			.owner(uncle)
			.inAccount(gasoline)
			.outAccount(savings)
			.date(date)
			.build();
		entryRestClient().insert(entryDebito);

		// executa a operacao a ser testada
		BigDecimal balance = balanceRestClient().balance(uncle, savings);

		assertThat(balance).isEqualTo(new BigDecimal(500).setScale(2));
	}

	@Test
	void balanceInvalid() {
		String ownerName = owner(OwnerConstants.MOTHER).name();
		String bank = category(CategoryConstants.BANK).description();
		String equityAccountDescription = equityAccount(EquityAccountConstants.SAVINGS, bank).description();

		// test blank fields
		balanceInvalid("", equityAccountDescription, HttpStatus.BAD_REQUEST);
		balanceInvalid(ownerName, "", HttpStatus.BAD_REQUEST);

		// test fields
		balanceInvalid("invalid owner", equityAccountDescription, HttpStatus.CONFLICT);
		balanceInvalid(ownerName, "invalid equity account", HttpStatus.CONFLICT);

		// test OwnerEquityAccountInitialValue
		balanceInvalid(ownerName, equityAccountDescription, HttpStatus.CONFLICT);
	}

	private void balanceInvalid(String ownerName, String equityAccountDescription, HttpStatus httpStatus) {
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> balanceRestClient().balance(ownerName, equityAccountDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

}
