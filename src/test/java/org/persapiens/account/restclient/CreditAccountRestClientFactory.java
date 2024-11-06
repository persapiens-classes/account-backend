package org.persapiens.account.restclient;

import java.util.Optional;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.CreditAccountDTO;

@SuperBuilder
@Data
public class CreditAccountRestClientFactory {

	private String protocol;

	private String servername;

	private int port;

	private CategoryRestClientFactory categoryRestClientFactory;

	public CreditAccountRestClient creditAccountRestClient() {
		return CreditAccountRestClient.builder()
			.restClientHelper(RestClientHelper.<CreditAccountDTO>builder()
				.endpoint("creditAccount")
				.protocol(this.protocol)
				.servername(this.servername)
				.port(this.port)
				.build())
			.build();
	}

	public CreditAccountDTO creditAccount(String description, String categoryDescription) {
		Optional<CreditAccountDTO> findByDescription = creditAccountRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			CategoryDTO category = this.categoryRestClientFactory.category(categoryDescription);
			CreditAccountDTO result = CreditAccountDTO.builder().description(description).category(category).build();
			return creditAccountRestClient().save(result);
		}
		else {
			return findByDescription.get();
		}
	}

}
