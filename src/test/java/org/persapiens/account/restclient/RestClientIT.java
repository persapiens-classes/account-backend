package org.persapiens.account.restclient;

import java.util.Optional;

import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.springframework.beans.factory.annotation.Value;

public class RestClientIT {

	private final String protocol = "http";

	private final String servername = "localhost";

	@Value("${local.server.port}")
	private int port;

	public <T> RestClientHelper<T> restClientHelper(String endpoint) {
		return RestClientHelper.<T>builder()
			.endpoint(endpoint)
			.protocol(this.protocol)
			.servername(this.servername)
			.port(this.port)
			.build();
	}

	public OwnerRestClient ownerRestClient() {
		return OwnerRestClient.builder().restClientHelper(this.<OwnerDTO>restClientHelper("owner")).build();
	}

	public OwnerDTO owner(String name) {
		Optional<OwnerDTO> findByDescription = ownerRestClient().findByName(name);
		if (findByDescription.isEmpty()) {
			OwnerDTO result = OwnerDTO.builder().name(name).build();
			return ownerRestClient().save(result);
		}
		else {
			return findByDescription.get();
		}
	}

	public CategoryRestClient categoryRestClient() {
		return CategoryRestClient.builder().restClientHelper(this.<CategoryDTO>restClientHelper("category")).build();
	}

	public CategoryDTO category(String description) {
		Optional<CategoryDTO> findByDescription = categoryRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			CategoryDTO result = CategoryDTO.builder().description(description).build();
			return categoryRestClient().save(result);
		}
		else {
			return findByDescription.get();
		}
	}

	public EquityAccountRestClient equityAccountRestClient() {
		return EquityAccountRestClient.builder()
			.restClientHelper(this.<EquityAccountDTO>restClientHelper("equityAccount"))
			.build();
	}

	public EquityAccountDTO equityAccount(String description, String categoryDescription) {
		Optional<EquityAccountDTO> findByDescription = equityAccountRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			CategoryDTO category = category(categoryDescription);
			EquityAccountDTO result = EquityAccountDTO.builder().description(description).category(category).build();
			return equityAccountRestClient().save(result);
		}
		else {
			return findByDescription.get();
		}
	}

	public CreditAccountRestClient creditAccountRestClient() {
		return CreditAccountRestClient.builder()
			.restClientHelper(this.<CreditAccountDTO>restClientHelper("creditAccount"))
			.build();
	}

	public CreditAccountDTO creditAccount(String description, String categoryDescription) {
		Optional<CreditAccountDTO> findByDescription = creditAccountRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			CategoryDTO category = category(categoryDescription);
			CreditAccountDTO result = CreditAccountDTO.builder().description(description).category(category).build();
			return creditAccountRestClient().save(result);
		}
		else {
			return findByDescription.get();
		}
	}

	public DebitAccountRestClient debitAccountRestClient() {
		return DebitAccountRestClient.builder()
			.restClientHelper(this.<DebitAccountDTO>restClientHelper("debitAccount"))
			.build();
	}

	public DebitAccountDTO debitAccount(String description, String categoryDescription) {
		Optional<DebitAccountDTO> findByDescription = debitAccountRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			CategoryDTO category = category(categoryDescription);
			DebitAccountDTO result = DebitAccountDTO.builder().description(description).category(category).build();
			return debitAccountRestClient().save(result);
		}
		else {
			return findByDescription.get();
		}
	}

	public OwnerEquityAccountInitialValueRestClient ownerEquityAccountInitialValueRestClient() {
		return OwnerEquityAccountInitialValueRestClient.builder()
			.restClientHelper(
					this.<OwnerEquityAccountInitialValueDTO>restClientHelper("ownerEquityAccountInitialValue"))
			.build();
	}

	public EntryRestClient entryRestClient() {
		return EntryRestClient.builder().restClientHelper(this.<EntryDTO>restClientHelper("entry")).build();
	}

}
