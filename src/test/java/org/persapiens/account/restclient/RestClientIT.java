package org.persapiens.account.restclient;

import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.persapiens.account.UserCredentials;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

class RestClientIT {

	@SuppressFBWarnings("SS_SHOULD_BE_STATIC")
	private final String protocol = "http";

	@SuppressFBWarnings("SS_SHOULD_BE_STATIC")
	private final String servername = "localhost";

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private UserCredentials userCredentials;

	<T> RestClientHelper<T> restClientHelper(String endpoint) {
		return RestClientHelper.<T>builder()
			.endpoint(endpoint)
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.username(this.userCredentials.getName())
			.password(this.userCredentials.getPassword())
			.build();
	}

	OwnerRestClient ownerRestClient() {
		return OwnerRestClient.builder().restClientHelper(this.<OwnerDTO>restClientHelper("owners")).build();
	}

	OwnerDTO owner(String name) {
		Optional<OwnerDTO> findByDescription = ownerRestClient().findByName(name);
		if (findByDescription.isEmpty()) {
			OwnerDTO result = OwnerDTO.builder().name(name).build();
			return ownerRestClient().insert(result);
		}
		else {
			return findByDescription.get();
		}
	}

	CategoryRestClient categoryRestClient() {
		return CategoryRestClient.builder().restClientHelper(this.<CategoryDTO>restClientHelper("categories")).build();
	}

	CategoryDTO category(String description) {
		Optional<CategoryDTO> findByDescription = categoryRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			CategoryDTO result = CategoryDTO.builder().description(description).build();
			return categoryRestClient().insert(result);
		}
		else {
			return findByDescription.get();
		}
	}

	EquityAccountRestClient equityAccountRestClient() {
		return EquityAccountRestClient.builder()
			.restClientHelper(this.<EquityAccountDTO>restClientHelper("equityAccounts"))
			.build();
	}

	EquityAccountDTO equityAccount(String description, String categoryDescription) {
		Optional<EquityAccountDTO> findByDescription = equityAccountRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			EquityAccountDTO result = EquityAccountDTO.builder()
				.description(description)
				.category(categoryDescription)
				.build();
			return equityAccountRestClient().insert(result);
		}
		else {
			return findByDescription.get();
		}
	}

	CreditAccountRestClient creditAccountRestClient() {
		return CreditAccountRestClient.builder()
			.restClientHelper(this.<CreditAccountDTO>restClientHelper("creditAccounts"))
			.build();
	}

	CreditAccountDTO creditAccount(String description, String categoryDescription) {
		Optional<CreditAccountDTO> findByDescription = creditAccountRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			CreditAccountDTO result = CreditAccountDTO.builder()
				.description(description)
				.category(categoryDescription)
				.build();
			return creditAccountRestClient().insert(result);
		}
		else {
			return findByDescription.get();
		}
	}

	DebitAccountRestClient debitAccountRestClient() {
		return DebitAccountRestClient.builder()
			.restClientHelper(this.<DebitAccountDTO>restClientHelper("debitAccounts"))
			.build();
	}

	DebitAccountDTO debitAccount(String description, String categoryDescription) {
		Optional<DebitAccountDTO> findByDescription = debitAccountRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			DebitAccountDTO result = DebitAccountDTO.builder()
				.description(description)
				.category(categoryDescription)
				.build();
			return debitAccountRestClient().insert(result);
		}
		else {
			return findByDescription.get();
		}
	}

	OwnerEquityAccountInitialValueRestClient ownerEquityAccountInitialValueRestClient() {
		return OwnerEquityAccountInitialValueRestClient.builder()
			.restClientHelper(
					this.<OwnerEquityAccountInitialValueDTO>restClientHelper("ownerEquityAccountInitialValues"))
			.build();
	}

	Optional<OwnerEquityAccountInitialValueDTO> ownerEquityAccountInitialValue(String ownerName,
			String equityAccountDescription) {
		return ownerEquityAccountInitialValueRestClient().findByOwnerAndEquityAccount(ownerName,
				equityAccountDescription);
	}

	EntryRestClient entryRestClient() {
		return EntryRestClient.builder().restClientHelper(this.<EntryDTO>restClientHelper("entries")).build();
	}

}
