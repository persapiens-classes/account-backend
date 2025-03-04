package org.persapiens.account.restclient;

import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.security.LoginResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;

class RestClientIT {

	private static final String PROTOCOL = "http";

	private static final String SERVERNAME = "localhost";

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private JwtTokenFactory jwtTokenFactory;

	<T> RestClientHelper<T> authenticatedRestClientHelper(String endpoint) {
		RestClientHelper<T> result = restClientHelper(endpoint);

		result.setJwtToken(this.jwtTokenFactory.getJwtToken(authenticationRestClient()));

		return result;
	}

	private <T> RestClientHelper<T> restClientHelper(String endpoint) {
		return RestClientHelper.<T>builder()
			.endpoint(endpoint)
			.protocol(PROTOCOL)
			.servername(SERVERNAME)
			.port(this.port)
			.build();
	}

	AuthenticationRestClient authenticationRestClient() {
		return AuthenticationRestClient.builder().restClientHelper(this.<LoginResponseDTO>restClientHelper("")).build();
	}

	OwnerRestClient ownerRestClient() {
		return OwnerRestClient.builder()
			.restClientHelper(this.<OwnerDTO>authenticatedRestClientHelper("owners"))
			.build();
	}

	OwnerDTO owner(String name) {
		try {
			return ownerRestClient().findByName(name);
		}
		catch (HttpClientErrorException error) {
			OwnerDTO result = new OwnerDTO(name);
			return ownerRestClient().insert(result);
		}
	}

	CategoryRestClient categoryRestClient() {
		return CategoryRestClient.builder()
			.restClientHelper(this.<CategoryDTO>authenticatedRestClientHelper("categories"))
			.build();
	}

	CategoryDTO category(String description) {
		try {
			return categoryRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			CategoryDTO result = new CategoryDTO(description);
			return categoryRestClient().insert(result);
		}
	}

	EquityAccountRestClient equityAccountRestClient() {
		return EquityAccountRestClient.builder()
			.restClientHelper(this.<EquityAccountDTO>authenticatedRestClientHelper("equityAccounts"))
			.build();
	}

	EquityAccountDTO equityAccount(String description, String categoryDescription) {
		try {
			return equityAccountRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			EquityAccountDTO result = new EquityAccountDTO(description, categoryDescription);
			return equityAccountRestClient().insert(result);
		}
	}

	CreditAccountRestClient creditAccountRestClient() {
		return CreditAccountRestClient.builder()
			.restClientHelper(this.<CreditAccountDTO>authenticatedRestClientHelper("creditAccounts"))
			.build();
	}

	CreditAccountDTO creditAccount(String description, String categoryDescription) {
		try {
			return creditAccountRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			CreditAccountDTO result = new CreditAccountDTO(description, categoryDescription);
			return creditAccountRestClient().insert(result);
		}
	}

	DebitAccountRestClient debitAccountRestClient() {
		return DebitAccountRestClient.builder()
			.restClientHelper(this.<DebitAccountDTO>authenticatedRestClientHelper("debitAccounts"))
			.build();
	}

	DebitAccountDTO debitAccount(String description, String categoryDescription) {
		try {
			return debitAccountRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			DebitAccountDTO result = new DebitAccountDTO(description, categoryDescription);
			return debitAccountRestClient().insert(result);
		}
	}

	OwnerEquityAccountInitialValueRestClient ownerEquityAccountInitialValueRestClient() {
		return OwnerEquityAccountInitialValueRestClient.builder()
			.restClientHelper(this
				.<OwnerEquityAccountInitialValueDTO>authenticatedRestClientHelper("ownerEquityAccountInitialValues"))
			.build();
	}

	OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue(String ownerName,
			String equityAccountDescription) {
		return ownerEquityAccountInitialValueRestClient().findByOwnerAndEquityAccount(ownerName,
				equityAccountDescription);
	}

	EntryRestClient entryRestClient() {
		return EntryRestClient.builder()
			.restClientHelper(this.<EntryDTO>authenticatedRestClientHelper("entries"))
			.build();
	}

}
