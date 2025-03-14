package org.persapiens.account.restclient;

import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.EntryDTO;
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

	CategoryRestClient creditCategoryRestClient() {
		return CategoryRestClient.builder()
			.restClientHelper(this.<CategoryDTO>authenticatedRestClientHelper("creditCategories"))
			.build();
	}

	CategoryRestClient debitCategoryRestClient() {
		return CategoryRestClient.builder()
			.restClientHelper(this.<CategoryDTO>authenticatedRestClientHelper("debitCategories"))
			.build();
	}

	CategoryRestClient equityCategoryRestClient() {
		return CategoryRestClient.builder()
			.restClientHelper(this.<CategoryDTO>authenticatedRestClientHelper("equityCategories"))
			.build();
	}

	CategoryDTO creditCategory(String description) {
		try {
			return creditCategoryRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			CategoryDTO result = new CategoryDTO(description);
			return creditCategoryRestClient().insert(result);
		}
	}

	CategoryDTO debitCategory(String description) {
		try {
			return debitCategoryRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			CategoryDTO result = new CategoryDTO(description);
			return debitCategoryRestClient().insert(result);
		}
	}
	
	CategoryDTO equityCategory(String description) {
		try {
			return equityCategoryRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			CategoryDTO result = new CategoryDTO(description);
			return equityCategoryRestClient().insert(result);
		}
	}

	AccountRestClient equityAccountRestClient() {
		return AccountRestClient.builder()
			.restClientHelper(this.<AccountDTO>authenticatedRestClientHelper("equityAccounts"))
			.build();
	}

	AccountDTO equityAccount(String description, String categoryDescription) {
		try {
			return equityAccountRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			AccountDTO result = new AccountDTO(description, categoryDescription);
			return equityAccountRestClient().insert(result);
		}
	}

	AccountRestClient creditAccountRestClient() {
		return AccountRestClient.builder()
			.restClientHelper(this.<AccountDTO>authenticatedRestClientHelper("creditAccounts"))
			.build();
	}

	AccountDTO creditAccount(String description, String categoryDescription) {
		try {
			return creditAccountRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			AccountDTO result = new AccountDTO(description, categoryDescription);
			return creditAccountRestClient().insert(result);
		}
	}

	AccountRestClient debitAccountRestClient() {
		return AccountRestClient.builder()
			.restClientHelper(this.<AccountDTO>authenticatedRestClientHelper("debitAccounts"))
			.build();
	}

	AccountDTO debitAccount(String description, String categoryDescription) {
		try {
			return debitAccountRestClient().findByDescription(description);
		}
		catch (HttpClientErrorException error) {
			AccountDTO result = new AccountDTO(description, categoryDescription);
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
