package org.persapiens.account.restclient;

import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.dto.DebitAccountDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DebitAccountRestClientIT {

	private final String protocol = "http";

	private final String servername = "localhost";

	@Value(value = "${local.server.port}")
	private int port;

	private CategoryRestClientFactory categoryRestClientFactory() {
		return CategoryRestClientFactory.builder().protocol(protocol).servername(servername).port(port).build();
	}

	private DebitAccountRestClient debitAccountRestClient() {
		return DebitAccountRestClientFactory.builder()
			.protocol(protocol)
			.servername(servername)
			.port(port)
			.build()
			.debitAccountRestClient();
	}

	@Test
	public void saveOne() {
		String description = "Uber";
		String categoryDescription = CategoryConstants.TRANSPORT;

		DebitAccountDTO debitAccount = DebitAccountDTO.builder()
			.description(description)
			.category(categoryRestClientFactory().category(categoryDescription))
			.build();

		// verify save operation
		assertThat(debitAccountRestClient().save(debitAccount)).isNotNull();

		// verify findByDescription operation
		assertThat(debitAccountRestClient().findByDescription(description).get().getDescription())
			.isEqualTo(debitAccount.getDescription());

		// verify findAll operation
		assertThat(debitAccountRestClient().findAll()).isNotEmpty();
	}

}