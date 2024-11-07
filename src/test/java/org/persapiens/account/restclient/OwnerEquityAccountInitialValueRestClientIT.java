package org.persapiens.account.restclient;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnerEquityAccountInitialValueRestClientIT extends RestClientIT {

	@Test
	void saveOne() {
		String mother = OwnerConstants.MOTHER;
		String savings = EquityAccountConstants.SAVINGS;
		String bank = CategoryConstants.BANK;

		OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue = OwnerEquityAccountInitialValueDTO.builder()
			.value(new BigDecimal(1000))
			.owner(owner(mother))
			.equityAccount(equityAccount(savings, bank))
			.build();

		// verify save operation
		assertThat(ownerEquityAccountInitialValueRestClient().save(ownerEquityAccountInitialValue)).isNotNull();

		// verify findAll operation
		assertThat(ownerEquityAccountInitialValueRestClient().findAll()).isNotEmpty();
	}

}
