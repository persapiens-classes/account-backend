package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.dto.EquityAccountDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquityAccountRestClientIT {

	private final String protocol = "http";

	private final String servername = "localhost";

	@Value("${local.server.port}")
	private int port;

	private CategoryRestClientFactory categoryRestClientFactory() {
		return CategoryRestClientFactory.builder()
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.build();
	}

	private EquityAccountRestClient equityAccountRestClient() {
		return EquityAccountRestClientFactory.builder()
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.build()
			.equityAccountRestClient();
	}

	@Test
	public void saveOne() {
		String description = "Super bank account";
		String categoryDescription = CategoryConstants.BANK;

		EquityAccountDTO equityAccount = EquityAccountDTO.builder()
			.description(description)
			.category(categoryRestClientFactory().category(categoryDescription))
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
