package org.persapiens.account.restclient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;

class RestClientIT {

	@SuppressFBWarnings("SS_SHOULD_BE_STATIC")
	private final String protocol = "http";

	@SuppressFBWarnings("SS_SHOULD_BE_STATIC")
	private final String servername = "localhost";

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private JwtTokenFactory jwtTokenFactory;

	<T> RestClientHelper<T> restClientHelper(String endpoint) {
		return RestClientHelper.<T>builder()
			.endpoint(endpoint)
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.jwtToken(this.jwtTokenFactory.getJwtToken(this.protocol, this.servername, this.port))
			.build();
	}

	OwnerRestClient ownerRestClient() {
		return OwnerRestClient.builder().restClientHelper(this.<OwnerDTO>restClientHelper("owners")).build();
	}

	OwnerDTO owner(String name) {
		try {
			return ownerRestClient().findByName(name);
		}
		catch (HttpClientErrorException error) {
			OwnerDTO result = OwnerDTO.builder().name(name).build();
			return ownerRestClient().insert(result);
		}
	}

	CategoryRestClient categoryRestClient() {
		return CategoryRestClient.builder().restClientHelper(this.<CategoryDTO>restClientHelper("categories")).build();
	}

	CategoryDTO category(String description) {
		try {
			return categoryRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			CategoryDTO result = CategoryDTO.builder().description(description).build();
			return categoryRestClient().insert(result);
		}
	}

	EquityAccountRestClient equityAccountRestClient() {
		return EquityAccountRestClient.builder()
			.restClientHelper(this.<EquityAccountDTO>restClientHelper("equityAccounts"))
			.build();
	}

	EquityAccountDTO equityAccount(String description, String categoryDescription) {
		try {
			return equityAccountRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			EquityAccountDTO result = EquityAccountDTO.builder()
				.description(description)
				.category(categoryDescription)
				.build();
			return equityAccountRestClient().insert(result);
		}
	}

	CreditAccountRestClient creditAccountRestClient() {
		return CreditAccountRestClient.builder()
			.restClientHelper(this.<CreditAccountDTO>restClientHelper("creditAccounts"))
			.build();
	}

	CreditAccountDTO creditAccount(String description, String categoryDescription) {
		try {
			return creditAccountRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			CreditAccountDTO result = CreditAccountDTO.builder()
				.description(description)
				.category(categoryDescription)
				.build();
			return creditAccountRestClient().insert(result);
		}
	}

	DebitAccountRestClient debitAccountRestClient() {
		return DebitAccountRestClient.builder()
			.restClientHelper(this.<DebitAccountDTO>restClientHelper("debitAccounts"))
			.build();
	}

	DebitAccountDTO debitAccount(String description, String categoryDescription) {
		try {
			return debitAccountRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			DebitAccountDTO result = DebitAccountDTO.builder()
				.description(description)
				.category(categoryDescription)
				.build();
			return debitAccountRestClient().insert(result);
		}
	}

	OwnerEquityAccountInitialValueRestClient ownerEquityAccountInitialValueRestClient() {
		return OwnerEquityAccountInitialValueRestClient.builder()
			.restClientHelper(
					this.<OwnerEquityAccountInitialValueDTO>restClientHelper("ownerEquityAccountInitialValues"))
			.build();
	}

	OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue(String ownerName,
			String equityAccountDescription) {
		return ownerEquityAccountInitialValueRestClient().findByOwnerAndEquityAccount(ownerName,
				equityAccountDescription);
	}

	EntryRestClient entryRestClient() {
		return EntryRestClient.builder().restClientHelper(this.<EntryDTO>restClientHelper("entries")).build();
	}

}
