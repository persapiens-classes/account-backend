package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.common.CreditCategoryConstants;
import org.persapiens.account.common.DebitAccountConstants;
import org.persapiens.account.common.DebitCategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.EquityCategoryConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.BalanceDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueInsertDTO;
import org.persapiens.account.service.OwnerEquityAccountInitialValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BalanceRestClientIT extends RestClientIT {

	@Autowired
	private OwnerEquityAccountInitialValueService ownerEquityAccountInitialValueService;

	private BalanceRestClient balanceRestClient() {
		return BalanceRestClient.builder().restClientHelper(this.<BigDecimal>authenticatedRestClientHelper("")).build();
	}

	private void createBalance500(String owner, String equityAccount) {
		// initial value 100
		OwnerEquityAccountInitialValueInsertDTO initialValue = new OwnerEquityAccountInitialValueInsertDTO(owner,
				equityAccount, new BigDecimal(100));
		ownerEquityAccountInitialValueRestClient().insert(initialValue);

		LocalDateTime date = LocalDateTime.now();

		// credit 600
		String internship = creditAccount(CreditAccountConstants.INTERNSHIP,
				creditCategory(CreditCategoryConstants.SALARY).description())
			.description();
		EntryInsertUpdateDTO creditEntry = new EntryInsertUpdateDTO(owner, owner, date, equityAccount, internship,
				new BigDecimal(600), "");
		creditEntryRestClient().insert(creditEntry);

		// debit 200
		String gasoline = debitAccount(DebitAccountConstants.GASOLINE,
				debitCategory(DebitCategoryConstants.TRANSPORT).description())
			.description();
		EntryInsertUpdateDTO debitEntry = new EntryInsertUpdateDTO(owner, owner, date, gasoline, equityAccount,
				new BigDecimal(200), "");
		debitEntryRestClient().insert(debitEntry);
	}

	@Test
	void balance500() {
		String uncle = owner(OwnerConstants.UNCLE).name();
		AccountDTO savings = equityAccount(EquityAccountConstants.SAVINGS,
				equityCategory(EquityCategoryConstants.BANK).description());

		createBalance500(uncle, savings.description());

		// executa a operacao a ser testada
		var balanceRestClient = balanceRestClient();
		BalanceDTO balance = balanceRestClient.balanceByOwnerAndEquityAccount(uncle, savings.description());

		assertThat(balance).isEqualTo(
				new BalanceDTO(uncle, savings, new BigDecimal(100).setScale(2), new BigDecimal(500).setScale(2)));
	}

	@Test
	void balanceInvalid() {
		String ownerName = owner(OwnerConstants.AUNT).name();
		String bank = equityCategory(EquityCategoryConstants.BANK).description();
		String equityAccountDescription = equityAccount(EquityAccountConstants.SAVINGS, bank).description();

		// test blank fields
		balanceInvalid("", equityAccountDescription, HttpStatus.BAD_REQUEST);
		balanceInvalid(ownerName, "", HttpStatus.BAD_REQUEST);

		// test fields
		balanceInvalid("invalid owner", equityAccountDescription, HttpStatus.NOT_FOUND);
		balanceInvalid(ownerName, "invalid equity account", HttpStatus.NOT_FOUND);

		// test OwnerEquityAccountInitialValue
		balanceInvalid(ownerName, equityAccountDescription, HttpStatus.NOT_FOUND);
	}

	private void balanceInvalid(String ownerName, String equityAccountDescription, HttpStatus httpStatus) {
		var balanceRestClient = balanceRestClient();
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> balanceRestClient.balanceByOwnerAndEquityAccount(ownerName, equityAccountDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void balances() {
		this.ownerEquityAccountInitialValueService.deleteAll();

		String grandfather = owner("grandfather").name();
		AccountDTO savings = equityAccount(EquityAccountConstants.SAVINGS,
				equityCategory(EquityCategoryConstants.BANK).description());

		createBalance500(grandfather, savings.description());

		var balanceRestClient = balanceRestClient();
		Iterable<BalanceDTO> balances = balanceRestClient.balanceAll();
		assertThat(balances).hasSize(1);
		assertThat(balances.iterator().next()).isEqualTo(
				new BalanceDTO(grandfather, savings, new BigDecimal(100).setScale(2), new BigDecimal(500).setScale(2)));
	}

}
