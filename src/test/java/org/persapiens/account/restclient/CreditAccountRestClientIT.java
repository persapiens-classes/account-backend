package org.persapiens.account.restclient;

import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.dto.CreditAccountDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreditAccountRestClientIT {

	private final String protocol = "http";

	private final String servername = "localhost";

	@Value(value = "${local.server.port}")
	private int port;

	private CategoryRestClientFactory categoryRestClientFactory() {
		return CategoryRestClientFactory.builder().protocol(protocol).servername(servername).port(port).build();
	}

	private CreditAccountRestClient creditAccountRestClient() {
		return CreditAccountRestClientFactory.builder()
			.protocol(protocol)
			.servername(servername)
			.port(port)
			.build()
			.creditAccountRestClient();
	}

	@Test
	public void saveOne() {
		String description = "New job";
		String categoryDescription = CategoryConstants.SALARY;

		CreditAccountDTO creditAccount = CreditAccountDTO.builder()
			.description(description)
			.category(categoryRestClientFactory().category(categoryDescription))
			.build();

		// verify save operation
		assertThat(creditAccountRestClient().save(creditAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(creditAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(creditAccount.getDescription());

		// verify findAll operation
		assertThat(creditAccountRestClient().findAll()).isNotEmpty();
	}

}