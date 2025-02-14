package org.persapiens.account.restclient;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnerEquityAccountInitialValueRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		OwnerDTO mother = owner(OwnerConstants.MOTHER);
		EquityAccountDTO savings = equityAccount(EquityAccountConstants.SAVINGS,
				category(CategoryConstants.BANK).getDescription());

		OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue = OwnerEquityAccountInitialValueDTO.builder()
			.value(new BigDecimal(1000))
			.owner(mother.getName())
			.equityAccount(savings.getDescription())
			.build();

		// verify insert operation
		assertThat(ownerEquityAccountInitialValueRestClient().insert(ownerEquityAccountInitialValue)).isNotNull();

		// verify findAll operation
		assertThat(ownerEquityAccountInitialValueRestClient().findAll()).isNotEmpty();
	}

	private void insertInvalid(BigDecimal value, String owner, String equityAccount, HttpStatus httpStatus) {
		OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValueDto = OwnerEquityAccountInitialValueDTO
			.builder()
			.value(value)
			.owner(owner)
			.equityAccount(equityAccount)
			.build();

		// verify insert operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> ownerEquityAccountInitialValueRestClient().insert(ownerEquityAccountInitialValueDto))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertInvalid() {
		OwnerDTO mother = owner(OwnerConstants.MOTHER);
		EquityAccountDTO savings = equityAccount(EquityAccountConstants.SAVINGS,
				category(CategoryConstants.BANK).getDescription());

		// null and empty
		insertInvalid(null, mother.getName(), savings.getDescription(), HttpStatus.BAD_REQUEST);
		insertInvalid(new BigDecimal(100), "", savings.getDescription(), HttpStatus.BAD_REQUEST);
		insertInvalid(new BigDecimal(100), mother.getName(), "", HttpStatus.BAD_REQUEST);

		// invalid owner and equity account
		insertInvalid(new BigDecimal(100), "invalid owner", savings.getDescription(), HttpStatus.CONFLICT);
		insertInvalid(new BigDecimal(100), mother.getName(), "invalid equity account", HttpStatus.CONFLICT);
	}

	@Test
	void insertSameOwnerEquityAccountInitialValueTwice() {
		OwnerDTO uncle = owner(OwnerConstants.UNCLE);
		EquityAccountDTO savings = equityAccount(EquityAccountConstants.INVESTIMENT,
				category(CategoryConstants.BANK).getDescription());

		OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue = OwnerEquityAccountInitialValueDTO.builder()
			.value(new BigDecimal(1000))
			.owner(uncle.getName())
			.equityAccount(savings.getDescription())
			.build();

		ownerEquityAccountInitialValueRestClient().insert(ownerEquityAccountInitialValue);

		// verify insert operation
		// verify status code error
		insertInvalid(new BigDecimal(1000), uncle.getName(), savings.getDescription(), HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		OwnerDTO aunt = owner(OwnerConstants.AUNT);
		EquityAccountDTO individualAssets = equityAccount(EquityAccountConstants.INDIVIDUAL_ASSETS,
				category(CategoryConstants.BANK).getDescription());

		OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue = OwnerEquityAccountInitialValueDTO.builder()
			.value(new BigDecimal(1000))
			.owner(aunt.getName())
			.equityAccount(individualAssets.getDescription())
			.build();

		OwnerEquityAccountInitialValueDTO inserted = ownerEquityAccountInitialValueRestClient()
			.insert(ownerEquityAccountInitialValue);
		inserted.setValue(new BigDecimal(2000));

		OwnerEquityAccountInitialValueDTO updated = ownerEquityAccountInitialValueRestClient().update(inserted);

		assertThat(updated.getValue()).isEqualTo(new BigDecimal(2000));
	}

	@Test
	void deleteOne() {
		OwnerDTO aunt = owner(OwnerConstants.AUNT);
		EquityAccountDTO investiment = equityAccount(EquityAccountConstants.INVESTIMENT,
				category(CategoryConstants.BANK).getDescription());

		OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue = OwnerEquityAccountInitialValueDTO.builder()
			.value(new BigDecimal(1000))
			.owner(aunt.getName())
			.equityAccount(investiment.getDescription())
			.build();

		OwnerEquityAccountInitialValueDTO bean = ownerEquityAccountInitialValueRestClient()
			.insert(ownerEquityAccountInitialValue);

		ownerEquityAccountInitialValueRestClient().deleteByOwnerAndEquityAccount(bean.getOwner(),
				bean.getEquityAccount());

		assertThat(ownerEquityAccountInitialValueRestClient().findByOwnerAndEquityAccount(bean.getOwner(),
				bean.getEquityAccount()))
			.isEmpty();
	}

	private void deleteInvalid(String ownerName, String equityAccountDescription, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> ownerEquityAccountInitialValueRestClient().deleteByOwnerAndEquityAccount(ownerName,
					equityAccountDescription))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String father = owner(OwnerConstants.FATHER).getName();
		String savings = equityAccount(EquityAccountConstants.INVESTIMENT,
				category(CategoryConstants.BANK).getDescription())
			.getDescription();

		deleteInvalid("", savings, HttpStatus.BAD_REQUEST);
		deleteInvalid(father, "", HttpStatus.BAD_REQUEST);
		deleteInvalid("invalid owner", savings, HttpStatus.CONFLICT);
		deleteInvalid(father, "invalid equity account", HttpStatus.CONFLICT);
		deleteInvalid(father, savings, HttpStatus.NOT_FOUND);
	}

}
