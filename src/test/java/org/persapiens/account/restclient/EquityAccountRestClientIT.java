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
public class EquityAccountRestClientIT extends RestClientIT {

	@Test
	public void saveOne() {
		String description = "Super bank account";
		String categoryDescription = CategoryConstants.BANK;

		EquityAccountDTO equityAccount = EquityAccountDTO.builder()
			.description(description)
			.category(category(categoryDescription))
			.build();

		// verify save operation
		assertThat(equityAccountRestClient().save(equityAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(equityAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(equityAccount.getDescription());

		// verify findAll operation
		assertThat(equityAccountRestClient().findAll()).isNotEmpty();
	}

}
