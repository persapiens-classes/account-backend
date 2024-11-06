package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EntryDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntryRestClientIT {

	private final String protocol = "http";

	private final String servername = "localhost";

	@Value("${local.server.port}")
	private int port;

	private EntryRestClient entryRestClient() {
		return EntryRestClientFactory.builder()
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.build()
			.entryRestClient();
	}

	private OwnerRestClientFactory ownerRestClientFactory() {
		return OwnerRestClientFactory.builder()
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.build();
	}

	private CategoryRestClientFactory categoryRestClientFactory() {
		return CategoryRestClientFactory.builder()
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.build();
	}

	private EquityAccountRestClientFactory equityAccountRestClientFactory() {
		return EquityAccountRestClientFactory.builder()
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.categoryRestClientFactory(categoryRestClientFactory())
			.build();
	}

	private CreditAccountRestClientFactory creditAccountRestClientFactory() {
		return CreditAccountRestClientFactory.builder()
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.categoryRestClientFactory(categoryRestClientFactory())
			.build();
	}

	@Test
	public void saveOne() {
		String mother = OwnerConstants.MOTHER;
		String internship = CreditAccountConstants.INTERNSHIP;
		String salary = CategoryConstants.SALARY;
		String savings = EquityAccountConstants.SAVINGS;
		String bank = CategoryConstants.BANK;

		EntryDTO entry = EntryDTO.builder()
			.value(new BigDecimal(543))
			.date(LocalDateTime.now())
			.note("saving the internship")
			.owner(ownerRestClientFactory().owner(mother))
			.inAccount(equityAccountRestClientFactory().equityAccount(savings, bank))
			.outAccount(creditAccountRestClientFactory().creditAccount(internship, salary))
			.build();

		// verify save operation
		assertThat(entryRestClient().save(entry)).isNotNull();

		// verify findAll operation
		assertThat(entryRestClient().findAll()).isNotEmpty();
	}

}
