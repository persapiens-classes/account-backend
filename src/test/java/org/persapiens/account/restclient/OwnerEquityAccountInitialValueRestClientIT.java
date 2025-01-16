package org.persapiens.account.restclient;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
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

	@Test
	void updateOne() {
		OwnerDTO mother = owner(OwnerConstants.AUNT);
		EquityAccountDTO individualAssets = equityAccount(EquityAccountConstants.INDIVIDUAL_ASSETS,
				category(CategoryConstants.BANK).getDescription());

		OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue = OwnerEquityAccountInitialValueDTO.builder()
			.value(new BigDecimal(1000))
			.owner(mother.getName())
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
		OwnerDTO mother = owner(OwnerConstants.AUNT);
		EquityAccountDTO investiment = equityAccount(EquityAccountConstants.INVESTIMENT,
				category(CategoryConstants.BANK).getDescription());

		OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue = OwnerEquityAccountInitialValueDTO.builder()
			.value(new BigDecimal(1000))
			.owner(mother.getName())
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

}
